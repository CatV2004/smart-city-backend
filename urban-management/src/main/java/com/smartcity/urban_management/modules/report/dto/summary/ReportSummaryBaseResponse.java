package com.smartcity.urban_management.modules.report.dto.summary;

import com.smartcity.urban_management.modules.report.entity.ReportStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ReportSummaryBaseResponse {

    private UUID id;
    private String title;
    private String categoryName;

    private String address;

    private String createdByName;
    private UUID createdByUserId;

    private LocalDateTime createdAt;
}