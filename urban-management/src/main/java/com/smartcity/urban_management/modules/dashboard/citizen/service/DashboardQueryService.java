package com.smartcity.urban_management.modules.dashboard.citizen.service;

import com.smartcity.urban_management.modules.dashboard.citizen.dto.ReportSummaryDto;
import com.smartcity.urban_management.modules.report.dto.RecentReportData;

import java.util.UUID;

public interface DashboardQueryService {

    ReportSummaryDto getSummary(UUID citizenId);

    RecentReportData getRecentReports(UUID citizenId);
}