package com.smartcity.urban_management.modules.report.service.impl;

import com.smartcity.urban_management.infrastructure.redis.cache.ReportCacheService;
import com.smartcity.urban_management.modules.category.entity.Category;
import com.smartcity.urban_management.modules.category.repository.CategoryRepository;
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
import com.smartcity.urban_management.modules.report.service.ReportStatusHistoryService;
import com.smartcity.urban_management.modules.user.entity.User;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository repository;
    private final ReportRepository reportRepository;
    private final CategoryRepository categoryRepository;
    private final AttachmentRepository attachmentRepository;
    private final EntityManager entityManager;
    private final ReportCacheService reportCacheService;
    private final ReportSortField reportSortField = new ReportSortField();
    private final ReportStatusHistoryService historyService;

    private final GeometryFactory geometryFactory = new GeometryFactory();

    @Override
    @Transactional
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
        createInitialHistory(report);

        return new CreateReportResponse(report.getId());
    }

    @Override
    public PageResponse<ReportAdminSummaryResponse> findAllForAdmin(
            ReportFilterRequest filter,
            PageRequestDto request
    ) {
        // ===== CREATE PAGEABLE =====
        Pageable pageable = PageableFactory.from(request, reportSortField);

        // ===== CLEAN KEYWORD =====
        String keywordParam = filter.getKeyword();
        if (keywordParam != null && keywordParam.isBlank()) {
            keywordParam = null;
        }

        // ===== CLEAN STATUSES =====
        Set<ReportStatus> statuses = filter.getStatuses();
        if (statuses != null && statuses.isEmpty()) {
            statuses = null;
        }

        // ===== DB QUERY (Projection) =====
        Page<ReportSummaryProjection> pageData =
                repository.findAllSummary(
                        statuses,
                        filter.getCategoryId(),
                        keywordParam,
                        pageable
                );

        // ===== MAP DTO =====
        Page<ReportAdminSummaryResponse> mapped =
                pageData.map(ReportSummaryMapper::toAdmin);

        return PageMapper.toResponse(mapped);
    }

    @Override
    public PageResponse<ReportCitizenSummaryResponse> findByUserId(
            UUID userId,
            ReportFilterRequest filter,
            PageRequestDto request
    ) {

        // ===== CREATE PAGEABLE =====
        Pageable pageable = PageableFactory.from(request, reportSortField);

        // ===== CLEAN KEYWORD =====
        String keywordParam = filter.getKeyword();
        if (keywordParam != null && keywordParam.isBlank()) {
            keywordParam = null;
        }

        // ===== STATUS FILTER =====
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

        return PageMapper.toResponse(mapped);
    }

    @Transactional
    @Override
    public ReportAdminDetailResponse getAdminDetail(UUID id) {
        ReportDetailProjection projection = getReportProjection(id);

        ReportAdminDetailResponse dto = ReportDetailMapper.toAdmin(projection);
        dto.setAttachments(getAttachments(id));

        return dto;
    }

    @Transactional
    @Override
    public ReportStaffDetailResponse getStaffDetail(UUID id) {
        ReportDetailProjection projection = getReportProjection(id);

        ReportStaffDetailResponse dto = ReportDetailMapper.toStaff(projection);
        dto.setAttachments(getAttachments(id));

        return dto;
    }

    @Transactional
    @Override
    public ReportCitizenDetailResponse getCitizenDetail(UUID id) {
        ReportDetailProjection projection = getReportProjection(id);

        ReportCitizenDetailResponse dto = ReportDetailMapper.toCitizen(projection);
        dto.setAttachments(getAttachments(id));

        return dto;
    }

    @Override
    @Transactional
    public ReportAdminDetailResponse adminUpdateStatus(
            UUID reportId,
            UpdateReportStatusRequest request,
            UUID adminId
    ) {
        // ===== GET REPORT =====
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new AppException(ErrorCode.REPORT_NOT_FOUND));

        // ===== UPDATE STATUS + HISTORY =====
        updateStatus(
                report,
                request.getStatus(),
                adminId.toString(),
                request.getNote()
        );

        // ===== SET APPROVED BY =====
        User adminRef = entityManager.getReference(User.class, adminId);
        report.setApprovedBy(adminRef);

        // ===== CACHE EVICTION =====
        reportCacheService.evictReport(reportId);
        reportCacheService.evictAllReportPages();

        return getAdminDetail(reportId);
    }

    @Override
    public ReportStaffDetailResponse staffUpdateStatus(
            UUID reportId,
            UpdateReportStatusRequest request
    ) {
        // ===== UPDATE ENTITY =====
        updateStatusInternal(reportId, request.getStatus());

        // ===== CACHE EVICTION =====
        reportCacheService.evictReport(reportId);
        reportCacheService.evictAllReportPages();

        // ===== RETURN CACHED DTO =====
        return getStaffDetail(reportId);
    }

    @Override
    public void softDeleteReport(UUID reportId, UUID userId) {
        Report report = repository.findByIdAndDeletedAtIsNull(reportId)
                .orElseThrow(() -> new AppException(ErrorCode.REPORT_NOT_FOUND));

        // business validation
        validateDeleteBusiness(report);

        report.setDeletedAt(LocalDateTime.now());
        report.setUpdatedAt(LocalDateTime.now());

        attachmentRepository.softDeleteByReportId(reportId);
    }

    @Override
    public void purgeDeletedReport(UUID id, UUID userId) {
        Report report = repository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.REPORT_NOT_FOUND));

        if (report.getDeletedAt() == null) {
            throw new AppException(ErrorCode.REPORT_NOT_SOFT_DELETED);
        }

        attachmentRepository.deleteByReportId(id);
        repository.delete(report);
    }

    @Override
    public RecentReportData getRecentReports(UUID citizenId, int limit) {

        List<ReportCitizenSummaryResponse> recentReports = repository.findRecentSummary(
                        citizenId,
                        PageRequest.of(0, limit)
                )
                .stream()
                .map(ReportSummaryMapper::toCitizen)
                .toList();

        // Wrap list vào DTO mới
        return RecentReportData.builder()
                .recentReportData(recentReports)
                .build();
    }

    @Override
    @Transactional
    public void updateStatus(
            Report report,
            ReportStatus newStatus,
            String changedBy,
            String note
    ) {
        ReportStatus oldStatus = report.getStatus();

        if (oldStatus == newStatus) return;

        report.setStatus(newStatus);
        reportRepository.save(report);

        historyService.createHistory(
                report,
                oldStatus,
                newStatus,
                changedBy,
                note
        );
    }

    @Override
    @Transactional
    public void updateFinalCategory(
            UUID reportId,
            UpdateFinalCategoryRequest request,
            UUID adminId
    ) {
//        Report report = reportRepository.findById(reportId)
//                .orElseThrow(() -> new AppException(ErrorCode.REPORT_NOT_FOUND));
//
//        Category finalCategory;
//
//        switch (request.getType()) {
//
//            case AI -> {
//                if (report.getAiCategory() == null) {
//                    throw new AppException(ErrorCode.AI_CATEGORY_ISNULL);
//                }
//                finalCategory = report.getAiCategory();
//            }
//
//            case USER -> {
//                if (report.getUserCategory() == null) {
//                    throw new AppException(ErrorCode.USER_CATEGORY_ISNULL);
//                }
//                finalCategory = report.getUserCategory();
//            }
//
//            case MANUAL -> {
//                if (request.getCategoryId() == null) {
//                    throw new AppException(ErrorCode.FINAL_CATEGORY_ID_REQUIRED);
//                }
//
//                finalCategory = categoryRepository.findById(request.getCategoryId())
//                        .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
//            }
//
//            default -> throw new AppException(ErrorCode.FINAL_CATEGORY_TYPE_INVALID);
//        }
//
//        // validate status
//        if (!Set.of(
//                ReportStatus.NEEDS_REVIEW,
//                ReportStatus.LOW_CONFIDENCE
//        ).contains(report.getStatus())) {
//            throw new AppException(ErrorCode.REPORT_STATUS_CHANGE_NOT_ALLOWED);
//        }
//
//        report.setFinalCategory(finalCategory);
//
//        // reuse method
//        updateStatus(
//                report,
//                ReportStatus.VERIFIED,
//                adminId.toString(),
//                request.getNote()
//        );
//
//        report.setApprovedBy(entityManager.getReference(User.class, adminId));
    }

    @Transactional
    public void updateFinalCategory(
            Report report,
            UpdateFinalCategoryRequest request,
            UUID adminId
    ) {

        Category finalCategory;

        switch (request.getType()) {

            case AI -> {
                if (report.getAiCategory() == null) {
                    throw new AppException(ErrorCode.AI_CATEGORY_ISNULL);
                }
                finalCategory = report.getAiCategory();
            }

            case USER -> {
                if (report.getUserCategory() == null) {
                    throw new AppException(ErrorCode.USER_CATEGORY_ISNULL);
                }
                finalCategory = report.getUserCategory();
            }

            case MANUAL -> {
                if (request.getCategoryId() == null) {
                    throw new AppException(ErrorCode.FINAL_CATEGORY_ID_REQUIRED);
                }

                finalCategory = categoryRepository.findById(request.getCategoryId())
                        .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
            }

            default -> throw new AppException(ErrorCode.FINAL_CATEGORY_TYPE_INVALID);
        }

        // validate status
        if (!Set.of(
                ReportStatus.NEEDS_REVIEW,
                ReportStatus.LOW_CONFIDENCE
        ).contains(report.getStatus())) {
            throw new AppException(ErrorCode.REPORT_STATUS_CHANGE_NOT_ALLOWED);
        }

        report.setFinalCategory(finalCategory);

        // reuse method
        updateStatus(
                report,
                ReportStatus.VERIFIED,
                adminId.toString(),
                request.getNote()
        );

        report.setApprovedBy(entityManager.getReference(User.class, adminId));
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

    private void createInitialHistory(Report report) {
        historyService.createHistory(
                report,
                null,
                report.getStatus(),
                "SYSTEM",
                "Report created"
        );
    }
}