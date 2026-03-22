package com.smartcity.urban_management.modules.report.dto.detail;

import com.smartcity.urban_management.modules.report.entity.Priority;
import com.smartcity.urban_management.modules.report.entity.ReportStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ReportAdminDetailResponse extends ReportDetailBaseResponse {

    private String userCategoryName;
    private String aiCategoryName;

    private Double aiConfidence;

    private String approvedByName;
    private UUID approvedById;

    private Priority priority;
    private ReportStatus status;
}
