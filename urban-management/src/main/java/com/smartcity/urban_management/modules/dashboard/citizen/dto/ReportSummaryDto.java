package com.smartcity.urban_management.modules.dashboard.citizen.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportSummaryDto {

    private long totalReports;
    private long pending;
    private long inProgress;
    private long resolved;
    private long rejected;

}