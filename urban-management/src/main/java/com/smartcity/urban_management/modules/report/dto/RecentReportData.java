package com.smartcity.urban_management.modules.report.dto;

import com.smartcity.urban_management.modules.report.dto.summary.ReportCitizenSummaryResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecentReportData {
    private List<ReportCitizenSummaryResponse> recentReportData;
}
