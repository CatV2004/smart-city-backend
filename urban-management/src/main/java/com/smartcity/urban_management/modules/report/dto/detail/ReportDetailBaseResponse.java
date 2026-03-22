package com.smartcity.urban_management.modules.report.dto.detail;

import com.smartcity.urban_management.modules.report.dto.summary.AttachmentSummaryResponse;
import com.smartcity.urban_management.modules.report.entity.ReportStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ReportDetailBaseResponse {

    private UUID id;
    private String title;
    private String description;
    private String categoryName;

    private List<AttachmentSummaryResponse> attachments;

    private Double latitude;
    private Double longitude;

    private String address;

    private String createdByName;
    private UUID createdByUserId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}