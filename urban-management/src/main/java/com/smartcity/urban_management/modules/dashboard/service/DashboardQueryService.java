package com.smartcity.urban_management.modules.dashboard.service;

import com.smartcity.urban_management.modules.dashboard.dto.ReportSummaryDto;
import com.smartcity.urban_management.modules.report.dto.ReportSummaryResponse;

import java.util.List;
import java.util.UUID;

public interface DashboardQueryService {

    ReportSummaryDto getSummary(UUID citizenId);

    List<ReportSummaryResponse> getRecentReports(UUID citizenId);
}