package com.smartcity.urban_management.modules.report.service;

import com.smartcity.urban_management.modules.report.dto.CreateReportRequest;
import com.smartcity.urban_management.modules.report.dto.ReportResponse;

import java.util.List;
import java.util.UUID;

public interface ReportService {

    ReportResponse create(CreateReportRequest request, UUID userId);

    List<ReportResponse> getAll();
}