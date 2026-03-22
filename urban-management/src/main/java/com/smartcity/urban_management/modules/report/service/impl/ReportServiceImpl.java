package com.smartcity.urban_management.modules.report.service.impl;

import com.smartcity.urban_management.infrastructure.redis.cache.ReportCacheService;
import com.smartcity.urban_management.modules.category.entity.Category;
import com.smartcity.urban_management.modules.report.dto.*;
import com.smartcity.urban_management.modules.report.dto.detail.*;
import com.smartcity.urban_management.modules.report.dto.summary.AttachmentSummaryResponse;
import com.smartcity.urban_management.modules.report.dto.summary.ReportAdminSummaryResponse;
import com.smartcity.urban_management.modules.report.dto.summary.ReportCitizenSummaryResponse;
import com.smartcity.urban_management.modules.report.dto.summary.ReportSummaryProjection;
import com.smartcity.urban_management.modules.report.entity.Report;
import com.smartcity.urban_management.modules.report.entity.ReportStatus;
import com.smartcity.urban_management.modules.report.mapper.ReportDetailMapper;
import com.smartcity.urban_management.modules.report.mapper.ReportSummaryMapper;
import com.smartcity.urban_management.modules.report.pagination.ReportSortField;
import com.smartcity.urban_management.modules.report.repository.AttachmentRepository;
import com.smartcity.urban_management.modules.report.repository.ReportRepository;
import com.smartcity.urban_management.modules.report.service.ReportService;
import com.smartcity.urban_management.modules.user.entity.RoleName;
import com.smartcity.urban_management.modules.user.entity.User;
import com.smartcity.urban_management.shared.event.ReportCreatedEvent;
import com.smartcity.urban_management.shared.exception.AppException;
import com.smartcity.urban_management.shared.exception.ErrorCode;
import com.smartcity.urban_management.shared.pagination.PageMapper;
import com.smartcity.urban_management.shared.pagination.PageRequestDto;
import com.smartcity.urban_management.shared.pagination.PageResponse;
import com.smartcity.urban_management.shared.pagination.PageableFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository repository;
    private final AttachmentRepository attachmentRepository;
    private final EntityManager entityManager;
    private final ReportCacheService reportCacheService;
    private final ReportSortField reportSortField = new ReportSortField();
    private final ApplicationEventPublisher applicationEventPublisher;

    private final GeometryFactory geometryFactory = new GeometryFactory();

    @Override
    public CreateReportResponse create(ReportCreateRequest request, UUID userId) {

        // ===== BUILD LOCATION =====
        Point point = geometryFactory.createPoint(
                new org.locationtech.jts.geom.Coordinate(
                        request.getLongitude(),
                        request.getLatitude()
                )
        );
        point.setSRID(4326);

        // ===== GET REFERENCE =====
        User userRef = entityManager.getReference(User.class, userId);
        Category cateRef = entityManager.getReference(Category.class, request.getUserCategoryId());

        // ===== BUILD ENTITY =====
        Report report = Report.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .userCategory(cateRef)
                .status(ReportStatus.PENDING)
                .location(point)
                .address(request.getAddress())
                .createdBy(userRef)
                .build();

        report = repository.save(report);

        // ===== PUBLISH EVENT =====
        applicationEventPublisher.publishEvent(
                new ReportCreatedEvent(
                        report.getId(),
                        userId,
                        report.getTitle(),
                        report.getDescription(),
                        request.getUserCategoryId(),
                        report.getLocation().getY(),
                        report.getLocation().getX(),
                        report.getAddress(),
                        report.getCreatedAt()
                )
        );

        // ===== EVICT CACHE =====
        reportCacheService.evictAllReportPages();
        reportCacheService.evictUserReportPages(userId);

        // ===== MAP DTO =====
        return new CreateReportResponse(report.getId());
    }

    @Override
    public PageResponse<ReportAdminSummaryResponse> findAllForAdmin(
            ReportFilterRequest filter,
            PageRequestDto request
    ) {

        int page = request.getPage();
        int size = request.getSize();
        String sort = request.getSort();
        String filterKey = buildAdminFilterKey(filter);

        // ===== CACHE CHECK =====
        Optional<PageResponse<ReportAdminSummaryResponse>> cached =
                reportCacheService.getReportPage(page, size, sort, filterKey, RoleName.ADMIN.name(), ReportAdminSummaryResponse.class);

        if (cached.isPresent()) {
            return cached.get();
        }

        // ===== CREATE PAGEABLE =====
        Pageable pageable = PageableFactory.from(request, reportSortField);

        // ===== UNWRAP KEYWORD =====
        String keywordParam = filter.getKeyword();
        if (keywordParam != null && keywordParam.isBlank()) {
            keywordParam = null;
        }

        // ===== DB QUERY (Projection) =====
        Page<ReportSummaryProjection> pageData =
                repository.findAllSummary(
                        filter.getStatus(),
                        filter.getCategoryId(),
                        keywordParam,
                        pageable
                );

        // ===== MAP DTO =====
        Page<ReportAdminSummaryResponse> mapped =
                pageData.map(ReportSummaryMapper::toAdmin);

        // ===== MAP RESPONSE =====
        PageResponse<ReportAdminSummaryResponse> response =
                PageMapper.toResponse(mapped);

        // ===== SAVE CACHE =====
        reportCacheService.cacheReportPage(
                page,
                size,
                sort,
                filterKey,
                RoleName.ADMIN.name(),
                response
        );

        return response;
    }

    @Override
    public PageResponse<ReportCitizenSummaryResponse> findByUserId(
            UUID userId,
            ReportFilterRequest filter,
            PageRequestDto request
    ) {

        int page = request.getPage();
        int size = request.getSize();
        String sort = request.getSort();

        String filterKey = buildCitizenFilterKey(filter);

        // ===== CACHE CHECK =====
        Optional<PageResponse<ReportCitizenSummaryResponse>> cached =
                reportCacheService.getUserReportPage(userId, page, size, sort, filterKey);

        if (cached.isPresent()) {
            return cached.get();
        }

        // ===== CREATE PAGEABLE =====
        Pageable pageable = PageableFactory.from(request, reportSortField);

        // ===== UNWRAP KEYWORD =====
        String keywordParam = filter.getKeyword();
        if (keywordParam != null && keywordParam.isBlank()) {
            keywordParam = null;
        }

        List<ReportStatus> statusesToSearch = null;
        if (filter.getDisplayStatus() != null) {
            statusesToSearch = new ArrayList<>(filter.getDisplayStatus().getOriginalStatuses());
        }

        // ===== DB QUERY (Projection) =====
        Page<ReportSummaryProjection> pageData =
                repository.findUserReportSummary(
                        userId,
                        statusesToSearch,
                        filter.getCategoryId(),
                        keywordParam,
                        pageable
                );

        // ===== MAP DTO =====
        Page<ReportCitizenSummaryResponse> mapped =
                pageData.map(ReportSummaryMapper::toCitizen);

        // ===== MAP RESPONSE =====
        PageResponse<ReportCitizenSummaryResponse> response =
                PageMapper.toResponse(mapped);

        // ===== SAVE CACHE =====
        reportCacheService.cacheUserReportPage(
                userId,
                page,
                size,
                sort,
                filterKey,
                response
        );

        return response;
    }

    @Override
    @Transactional
    public ReportAdminDetailResponse getAdminDetail(UUID id) {

        Optional<ReportAdminDetailResponse> cached =
                reportCacheService.getReport(id, RoleName.ADMIN.name(), ReportAdminDetailResponse.class);

        if (cached.isPresent()) {
            return cached.get();
        }

        ReportDetailProjection p = getReportProjection(id);

        ReportAdminDetailResponse dto = ReportDetailMapper.toAdmin(p);
        dto.setAttachments(getAttachments(id));

        reportCacheService.cacheReport(id, RoleName.ADMIN.name(), dto);

        return dto;
    }

    @Override
    @Transactional
    public ReportStaffDetailResponse getStaffDetail(UUID id) {

        Optional<ReportStaffDetailResponse> cached =
                reportCacheService.getReport(id, RoleName.STAFF.name(), ReportStaffDetailResponse.class);

        if (cached.isPresent()) {
            return cached.get();
        }

        ReportDetailProjection p = getReportProjection(id);

        ReportStaffDetailResponse dto = ReportDetailMapper.toStaff(p);
        dto.setAttachments(getAttachments(id));

        reportCacheService.cacheReport(id, RoleName.STAFF.name(), dto);

        return dto;
    }

    @Override
    @Transactional
    public ReportCitizenDetailResponse getCitizenDetail(UUID id) {

        Optional<ReportCitizenDetailResponse> cached =
                reportCacheService.getReport(id, RoleName.CITIZEN.name(), ReportCitizenDetailResponse.class);

        if (cached.isPresent()) {
            return cached.get();
        }

        ReportDetailProjection p = getReportProjection(id);

        ReportCitizenDetailResponse dto = ReportDetailMapper.toCitizen(p);
        dto.setAttachments(getAttachments(id));

        reportCacheService.cacheReport(id, RoleName.CITIZEN.name(), dto);

        return dto;
    }

    @Transactional
    @Override
    public ReportAdminDetailResponse adminUpdateStatus(
            UUID reportId,
            UpdateReportStatusRequest request,
            UUID adminId
    ) {

        Report report = updateStatusInternal(reportId, request.getStatus());

        User adminRef = entityManager.getReference(User.class, adminId);

        report.setApprovedBy(adminRef);

        reportCacheService.evictReport(reportId);
        reportCacheService.evictAllReportPages();

        return getAdminDetail(reportId);
    }

    @Transactional
    @Override
    public ReportStaffDetailResponse staffUpdateStatus(
            UUID reportId,
            UpdateReportStatusRequest request
    ) {

        Report report = updateStatusInternal(reportId, request.getStatus());

        reportCacheService.evictReport(reportId);
        reportCacheService.evictAllReportPages();

        return getStaffDetail(reportId);
    }

    @Override
    @Transactional
    public void softDeleteReport(UUID reportId, UUID userId) {

        Report report = repository
                .findByIdAndDeletedAtIsNull(reportId)
                .orElseThrow(() ->
                        new AppException(ErrorCode.REPORT_NOT_FOUND));

        // ===== business validation =====
        validateDeleteBusiness(report);

        // ===== soft delete =====
        report.setDeletedAt(LocalDateTime.now());
        report.setUpdatedAt(LocalDateTime.now());

        // ===== CACHE EVICT =====
        reportCacheService.evictReport(reportId);
        reportCacheService.evictAllReportPages();
        reportCacheService.evictUserReportPages(userId);

        attachmentRepository.softDeleteByReportId(reportId);
    }

    @Override
    @Transactional
    public void purgeDeletedReport(UUID id, UUID userId) {

        Report report = repository
                .findById(id)
                .orElseThrow(() ->
                        new AppException(ErrorCode.REPORT_NOT_FOUND));

        if (report.getDeletedAt() == null) {
            throw new AppException(ErrorCode.REPORT_NOT_SOFT_DELETED);
        }

        attachmentRepository.deleteByReportId(id);
        reportCacheService.evictReport(id);
        reportCacheService.evictAllReportPages();
        reportCacheService.evictUserReportPages(userId);

        repository.delete(report);
    }

    @Override
    public List<ReportCitizenSummaryResponse> getRecentReports(UUID citizenId, int limit) {
        return repository.findRecentSummary(
                        citizenId,
                        PageRequest.of(0, limit)
                )
                .stream()
                .map(ReportSummaryMapper::toCitizen) //
                .toList();
    }

    private void validateStatusTransition(
            ReportStatus current,
            ReportStatus target
    ) {

        switch (current) {

            // ========== INITIAL ==========
            case PENDING -> {
                if (target != ReportStatus.VERIFIED_AUTO &&
                        target != ReportStatus.NEEDS_REVIEW &&
                        target != ReportStatus.LOW_CONFIDENCE) {
                    throw new AppException(ErrorCode.INVALID_REPORT_STATUS);
                }
            }

            // ========== AI AUTO ==========
            case VERIFIED_AUTO -> {
                if (target != ReportStatus.ASSIGNED) {
                    throw new AppException(ErrorCode.INVALID_REPORT_STATUS);
                }
            }

            // ========== NEED HUMAN ==========
            case NEEDS_REVIEW, LOW_CONFIDENCE -> {
                if (target != ReportStatus.VERIFIED &&
                        target != ReportStatus.REJECTED) {
                    throw new AppException(ErrorCode.INVALID_REPORT_STATUS);
                }
            }

            // ========== VERIFIED ==========
            case VERIFIED -> {
                if (target != ReportStatus.ASSIGNED) {
                    throw new AppException(ErrorCode.INVALID_REPORT_STATUS);
                }
            }

            // ========== TASK FLOW ==========
            case ASSIGNED -> {
                if (target != ReportStatus.IN_PROGRESS) {
                    throw new AppException(ErrorCode.INVALID_REPORT_STATUS);
                }
            }

            case IN_PROGRESS -> {
                if (target != ReportStatus.RESOLVED) {
                    throw new AppException(ErrorCode.INVALID_REPORT_STATUS);
                }
            }

            case RESOLVED -> {
                if (target != ReportStatus.CLOSED) {
                    throw new AppException(ErrorCode.INVALID_REPORT_STATUS);
                }
            }

            // ========== FINAL ==========
            case REJECTED, CLOSED -> {
                throw new AppException(ErrorCode.REPORT_STATUS_CHANGE_NOT_ALLOWED);
            }

            default -> throw new AppException(ErrorCode.REPORT_STATUS_CHANGE_NOT_ALLOWED);
        }
    }

    private void validateDeleteBusiness(Report report) {

        if (report.getDeletedAt() != null) {
            throw new AppException(ErrorCode.REPORT_ALREADY_DELETED);
        }

        if (report.getStatus() == ReportStatus.RESOLVED) {
            throw new AppException(ErrorCode.REPORT_DELETE_NOT_ALLOWED);
        }
    }

    private String buildCitizenFilterKey(ReportFilterRequest filter) {
        if (filter == null) {
            return "nofilter";
        }

        String keyword = filter.getKeyword();
        keyword = keyword != null ? keyword.trim() : "";

        String displayStatusKey = filter.getDisplayStatus() != null
                ? filter.getDisplayStatus().name()
                : "ALL";

        return String.format(
                "displayStatus:%s|category:%s|keyword:%s",
                displayStatusKey,
                filter.getCategoryId() != null ? filter.getCategoryId().toString() : "ALL",
                keyword
        );
    }

    private String buildAdminFilterKey(ReportFilterRequest filter) {
        if (filter == null) {
            return "nofilter";
        }

        String keyword = filter.getKeyword();
        keyword = keyword != null ? keyword.trim() : "";

        String statusKey = filter.getStatus() != null
                ? filter.getStatus().name()
                : "ALL";

        return String.format(
                "status:%s|category:%s|keyword:%s",
                statusKey,
                filter.getCategoryId() != null ? filter.getCategoryId().toString() : "ALL",
                keyword
        );
    }

    private ReportDetailProjection getReportProjection(UUID id) {
        return repository.findDetailProjection(id)
                .orElseThrow(() -> new AppException(ErrorCode.REPORT_NOT_FOUND));
    }

    private List<AttachmentSummaryResponse> getAttachments(UUID reportId) {
        return attachmentRepository.findAllByReportId(reportId);
    }

    private Report updateStatusInternal(
            UUID reportId,
            ReportStatus newStatus
    ) {
        Report report = repository.findById(reportId)
                .orElseThrow(() -> new AppException(ErrorCode.REPORT_NOT_FOUND));

        validateStatusTransition(report.getStatus(), newStatus);

        report.setStatus(newStatus);

        return report;
    }
}