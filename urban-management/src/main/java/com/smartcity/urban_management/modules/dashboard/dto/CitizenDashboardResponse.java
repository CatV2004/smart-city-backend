package com.smartcity.urban_management.modules.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class CitizenDashboardResponse {

    private ReportSummaryDto summary;

    private List<RecentReportDto> recentReports;

    private List<CategoryCountDto> categoryBreakdown;

}
