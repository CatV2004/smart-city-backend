package com.smartcity.urban_management.modules.report.service;

import com.smartcity.urban_management.modules.report.dto.ReportCreateRequest;
import com.smartcity.urban_management.modules.report.dto.ReportDetailResponse;
import com.smartcity.urban_management.modules.report.dto.ReportSummaryResponse;
import com.smartcity.urban_management.modules.report.dto.UpdateReportStatusRequest;
import com.smartcity.urban_management.shared.pagination.PageRequestDto;
import com.smartcity.urban_management.shared.pagination.PageResponse;

import java.util.List;
import java.util.UUID;

public interface ReportService {

    ReportSummaryResponse create(ReportCreateRequest request, UUID userId);

    PageResponse<ReportSummaryResponse> findAll(PageRequestDto request);

    ReportDetailResponse findById(String reportId);

    ReportDetailResponse updateStatus(UUID reportId, UpdateReportStatusRequest request, UUID adminId);

    void softDeleteReport(UUID reportId, UUID userId);

    void purgeDeletedReport(UUID reportId, UUID userId);

    PageResponse<ReportSummaryResponse> findByUserId(UUID userId, PageRequestDto request);
}