package com.smartcity.urban_management.modules.report.service;

import com.smartcity.urban_management.modules.report.dto.*;
import com.smartcity.urban_management.shared.pagination.PageRequestDto;
import com.smartcity.urban_management.shared.pagination.PageResponse;

import java.util.List;
import java.util.UUID;

public interface ReportService {

    ReportSummaryResponse create(ReportCreateRequest request, UUID userId);

    PageResponse<ReportSummaryResponse> findAll(ReportFilterRequest filter, PageRequestDto request);

    ReportDetailResponse findById(String reportId);

    ReportDetailResponse updateStatus(UUID reportId, UpdateReportStatusRequest request, UUID adminId);

    void softDeleteReport(UUID reportId, UUID userId);

    void purgeDeletedReport(UUID reportId, UUID userId);

    PageResponse<ReportSummaryResponse> findByUserId(UUID userId, ReportFilterRequest filter,  PageRequestDto request);

    List<ReportSummaryResponse> getRecentReports(UUID citizenId, int limit);
}