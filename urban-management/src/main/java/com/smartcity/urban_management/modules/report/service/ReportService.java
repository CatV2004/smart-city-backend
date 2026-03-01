package com.smartcity.urban_management.modules.report.service;

import com.smartcity.urban_management.modules.report.dto.ReportCreateRequest;
import com.smartcity.urban_management.modules.report.dto.ReportDetailResponse;
import com.smartcity.urban_management.modules.report.dto.ReportSummaryResponse;
import com.smartcity.urban_management.modules.report.dto.UpdateReportStatusRequest;

import java.util.List;
import java.util.UUID;

public interface ReportService {

    ReportSummaryResponse create(ReportCreateRequest request, UUID userId);

    List<ReportSummaryResponse> findAll();

    ReportDetailResponse findById(String reportId);

    ReportDetailResponse updateStatus(UUID reportId, UpdateReportStatusRequest request, UUID adminId);

    void softDeleteReport(UUID reportId);

    void purgeDeletedReport(UUID reportId);
}