package com.smartcity.urban_management.modules.report.dto.detail;

import com.smartcity.urban_management.modules.report.dto.ReportDisplayStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ReportCitizenDetailResponse extends ReportDetailBaseResponse {
    private ReportDisplayStatus status;
}