package com.smartcity.urban_management.modules.report.dto.summary;

import com.smartcity.urban_management.modules.report.entity.ReportStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ReportAdminSummaryResponse extends ReportSummaryBaseResponse {

    private ReportStatus status;
}