package com.smartcity.urban_management.modules.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ReportSummaryDto {

    private long totalReports;
    private long pending;
    private long inProgress;
    private long resolved;
    private long rejected;

}