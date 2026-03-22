package com.smartcity.urban_management.modules.report.dto.detail;

import com.smartcity.urban_management.modules.report.entity.ReportStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ReportStaffDetailResponse extends ReportDetailBaseResponse {
    private String approvedByName;
    private ReportStatus status;
}
