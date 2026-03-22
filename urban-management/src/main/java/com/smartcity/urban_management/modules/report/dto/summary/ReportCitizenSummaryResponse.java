package com.smartcity.urban_management.modules.report.dto.summary;

import com.smartcity.urban_management.modules.report.dto.ReportDisplayStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
public class ReportCitizenSummaryResponse extends ReportSummaryBaseResponse {
    private String description;
    private ReportDisplayStatus status;
}