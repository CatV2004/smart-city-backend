package com.smartcity.urban_management.modules.report.service;

import com.smartcity.urban_management.modules.report.dto.*;
import com.smartcity.urban_management.modules.report.dto.detail.ReportAdminDetailResponse;
import com.smartcity.urban_management.modules.report.dto.detail.ReportCitizenDetailResponse;
import com.smartcity.urban_management.modules.report.dto.summary.ReportAdminSummaryResponse;
import com.smartcity.urban_management.modules.report.dto.summary.ReportCitizenSummaryResponse;
import com.smartcity.urban_management.modules.report.entity.Report;
import com.smartcity.urban_management.modules.report.entity.ReportStatus;
import com.smartcity.urban_management.shared.pagination.PageRequestDto;
import com.smartcity.urban_management.shared.pagination.PageResponse;

import java.util.UUID;

public interface ReportService {

    CreateReportResponse create(ReportCreateRequest request, UUID userId);

    PageResponse<ReportAdminSummaryResponse> findAllForAdmin(ReportFilterRequest filter, PageRequestDto request);

    ReportAdminDetailResponse getAdminDetail(UUID id);

    ReportCitizenDetailResponse getCitizenDetail(UUID id);

    void updateStatus(Report report, ReportStatus newStatus, String changedBy, String note);

    void updateFinalCategory(UUID reportId, UpdateFinalCategoryRequest finalCateRequest, UUID adminId);

    ReportAdminDetailResponse adminUpdateStatus(UUID reportId, UpdateReportStatusRequest request, UUID adminId);

    void softDeleteReport(UUID reportId, UUID userId);

    void purgeDeletedReport(UUID reportId, UUID userId);

    PageResponse<ReportCitizenSummaryResponse> findByUserId(UUID userId, ReportFilterRequest filter, PageRequestDto request);

    RecentReportData getRecentReports(UUID citizenId, int limit);
}