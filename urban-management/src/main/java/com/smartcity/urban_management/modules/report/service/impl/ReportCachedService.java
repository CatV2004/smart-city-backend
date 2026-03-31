package com.smartcity.urban_management.modules.report.service.impl;

import com.smartcity.urban_management.infrastructure.redis.cache.MapCacheService;
import com.smartcity.urban_management.infrastructure.redis.cache.ReportCacheService;
import com.smartcity.urban_management.modules.report.dto.*;
import com.smartcity.urban_management.modules.report.dto.detail.ReportAdminDetailResponse;
import com.smartcity.urban_management.modules.report.dto.detail.ReportCitizenDetailResponse;
import com.smartcity.urban_management.modules.report.dto.summary.ReportAdminSummaryResponse;
import com.smartcity.urban_management.modules.report.dto.summary.ReportCitizenSummaryResponse;
import com.smartcity.urban_management.modules.report.entity.Report;
import com.smartcity.urban_management.modules.report.entity.ReportStatus;
//import com.smartcity.urban_management.modules.report.event.ReportStatusChangedEvent;
import com.smartcity.urban_management.modules.report.repository.ReportRepository;
import com.smartcity.urban_management.modules.report.service.ReportService;
import com.smartcity.urban_management.modules.user.entity.RoleName;
import com.smartcity.urban_management.shared.exception.AppException;
import com.smartcity.urban_management.shared.exception.ErrorCode;
import com.smartcity.urban_management.shared.messaging.event.ReportCreatedEvent;
import com.smartcity.urban_management.shared.messaging.event.ReportStatusChangedEvent;
import com.smartcity.urban_management.shared.pagination.PageRequestDto;
import com.smartcity.urban_management.shared.pagination.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Primary
@Slf4j
public class ReportCachedService implements ReportService {

    private final ReportServiceImpl delegate;
    private final ReportRepository reportRepository;
    private final ReportCacheService reportCacheService;
    private final MapCacheService mapCacheService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Transactional
    public CreateReportResponse create(ReportCreateRequest request, UUID userId) {
        // ===== CREATE REPORT DB =====
        CreateReportResponse response = delegate.create(request, userId);

        // ===== PUBLISH EVENT =====
        applicationEventPublisher.publishEvent(
                new ReportCreatedEvent(
                        response.id(),
                        userId,
                        request.getTitle(),
                        request.getDescription(),
                        request.getUserCategoryId(),
                        request.getLatitude(),
                        request.getLongitude(),
                        request.getAddress(),
                        LocalDateTime.now()
                )
        );

        // ===== EVICT CACHE =====
        reportCacheService.evictAllReportPages();
        reportCacheService.evictUserReportPages(userId);
        mapCacheService.evictAllMapData();

        return response;
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
                reportCacheService.getReportPage(page, size, sort, filterKey,
                        RoleName.ADMIN.name(), ReportAdminSummaryResponse.class);

        if (cached.isPresent()) {
            return cached.get();
        }

        // ===== CACHE MISS → delegate query =====
        PageResponse<ReportAdminSummaryResponse> response =
                delegate.findAllForAdmin(filter, request);

        // ===== CACHE SET =====
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

        // ===== CACHE MISS → delegate query =====
        PageResponse<ReportCitizenSummaryResponse> response =
                delegate.findByUserId(userId, filter, request);

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
    public ReportAdminDetailResponse getAdminDetail(UUID id) {
        Optional<ReportAdminDetailResponse> cached =
                reportCacheService.getReport(id, RoleName.ADMIN.name(), ReportAdminDetailResponse.class);

        if (cached.isPresent()) {
            return cached.get();
        }

        // ===== CACHE MISS → delegate =====
        ReportAdminDetailResponse dto = delegate.getAdminDetail(id);

        reportCacheService.cacheReport(id, RoleName.ADMIN.name(), dto);

        return dto;
    }

    @Override
    public ReportCitizenDetailResponse getCitizenDetail(UUID id) {
        Optional<ReportCitizenDetailResponse> cached =
                reportCacheService.getReport(id, RoleName.CITIZEN.name(), ReportCitizenDetailResponse.class);

        if (cached.isPresent()) {
            return cached.get();
        }

        ReportCitizenDetailResponse dto = delegate.getCitizenDetail(id);

        reportCacheService.cacheReport(id, RoleName.CITIZEN.name(), dto);

        return dto;
    }

    @Override
    @Transactional
    public ReportAdminDetailResponse adminUpdateStatus(
            UUID reportId,
            UpdateReportStatusRequest request,
            UUID adminId
    ) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new AppException(ErrorCode.REPORT_NOT_FOUND));

        // ===== DELEGATE: update entity =====
        delegate.adminUpdateStatus(report, request, adminId);

        // ===== UPDATE STATUS + HISTORY =====
        updateStatus(
                report,
                request.getStatus(),
                adminId.toString(),
                request.getNote()
        );

        // ===== CACHE EVICITON =====
        reportCacheService.evictReport(reportId);
        reportCacheService.evictAllReportPages();
        mapCacheService.evictAllMapData();

        // ===== RETURN CACHED DTO =====
        return getAdminDetail(reportId);
    }

    @Override
    @Transactional
    public void updateStatus(
            Report report,
            ReportStatus newStatus,
            String changedBy,
            String note
    ) {
        // ===== DELEGATE BUSINESS LOGIC =====
        delegate.updateStatus(report, newStatus, changedBy, note);
        applicationEventPublisher.publishEvent(
                new ReportStatusChangedEvent(
                        report.getId(),
                        report.getStatus(),
                        newStatus,
                        changedBy
                )
        );

        // ===== CACHE EVICTION =====
        UUID reportId = report.getId();

        reportCacheService.evictReport(reportId);
        reportCacheService.evictAllReportPages();
        mapCacheService.evictAllMapData();

        if (report.getCreatedBy() != null) {
            UUID userId = report.getCreatedBy().getId();
            reportCacheService.evictUserReportPages(userId);
            reportCacheService.evictRecentReports(userId);
        }
    }

    @Override
    @Transactional
    public void updateFinalCategory(
            UUID reportId,
            UpdateFinalCategoryRequest request,
            UUID adminId
    ) {
        // ===== GET OLD STATUS (để publish event) =====
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new AppException(ErrorCode.REPORT_NOT_FOUND));

        // ===== DELEGATE =====
        delegate.updateFinalCategory(report, request, adminId);

        // ===== UPDATE STATUS =====
        updateStatus(
                report,
                ReportStatus.VERIFIED,
                adminId.toString(),
                request.getNote()
        );

        // ===== CACHE EVICTION =====
        reportCacheService.evictReport(reportId);
        reportCacheService.evictAllReportPages();
        mapCacheService.evictAllMapData();

        if (report.getCreatedBy() != null) {
            UUID userId = report.getCreatedBy().getId();
            reportCacheService.evictUserReportPages(userId);
            reportCacheService.evictRecentReports(userId);
        }
    }

    @Override
    @Transactional
    public void softDeleteReport(UUID reportId, UUID userId) {
        // ===== delegate DB =====
        delegate.softDeleteReport(reportId, userId);

        // ===== cache eviction =====
        reportCacheService.evictReport(reportId);
        reportCacheService.evictAllReportPages();
        reportCacheService.evictUserReportPages(userId);
    }

    @Override
    @Transactional
    public void purgeDeletedReport(UUID id, UUID userId) {
        // ===== delegate DB =====
        delegate.purgeDeletedReport(id, userId);

        // ===== cache eviction =====
        reportCacheService.evictReport(id);
        reportCacheService.evictAllReportPages();
        reportCacheService.evictUserReportPages(userId);
        mapCacheService.evictAllMapData();
    }

    @Override
    public RecentReportData getRecentReports(UUID citizenId, int limit) {
        // ===== TRY CACHE =====
        Optional<RecentReportData> cached =
                reportCacheService.getRecentReports(citizenId, limit);

        if (cached.isPresent()) {
            return cached.get();
        }

        // ===== DELEGATE =====
        RecentReportData result =
                delegate.getRecentReports(citizenId, limit);

        // ===== CACHE =====
        reportCacheService.cacheRecentReports(citizenId, limit, result);

        return result;
    }

    private String buildCitizenFilterKey(ReportFilterRequest filter) {
        if (filter == null) {
            return "v1|type:citizen|nofilter";
        }

        String keyword = normalizeKeyword(filter.getKeyword());

        String displayStatusKey = filter.getDisplayStatus() != null
                ? filter.getDisplayStatus().name()
                : "ALL";

        String categoryKey = filter.getCategoryId() != null
                ? filter.getCategoryId().toString()
                : "ALL";

        return String.format(
                "v1|type:citizen|displayStatus:%s|category:%s|keyword:%s",
                displayStatusKey,
                categoryKey,
                keyword
        );
    }

    private String buildAdminFilterKey(ReportFilterRequest filter) {
        if (filter == null) {
            return "v1|type:admin|nofilter";
        }

        String keyword = normalizeKeyword(filter.getKeyword());

        Set<ReportStatus> statuses = filter.getStatuses();
        String statusKey = (statuses != null && !statuses.isEmpty())
                ? statuses.stream()
                .map(ReportStatus::name)
                .sorted()
                .collect(Collectors.joining(","))
                : "ALL";

        String categoryKey = filter.getCategoryId() != null
                ? filter.getCategoryId().toString()
                : "ALL";

        return String.format(
                "v1|type:admin|status:%s|category:%s|keyword:%s",
                statusKey,
                categoryKey,
                keyword
        );
    }

    private String normalizeKeyword(String keyword) {
        if (keyword == null) return "";
        return keyword.trim()
                .toLowerCase()
                .replaceAll("\\s+", " ");
    }

}
