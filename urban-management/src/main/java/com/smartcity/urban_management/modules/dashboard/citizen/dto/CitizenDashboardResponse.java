package com.smartcity.urban_management.modules.dashboard.citizen.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CitizenDashboardResponse {

    private ReportSummaryDto summary;

    private List<RecentReportDto> recentReports;

    private List<CategoryCountDto> categoryBreakdown;

}
