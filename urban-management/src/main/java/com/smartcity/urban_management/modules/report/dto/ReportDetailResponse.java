package com.smartcity.urban_management.modules.report.dto;

import com.smartcity.urban_management.modules.report.entity.ReportStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportDetailResponse {

    private UUID id;
    private String title;
    private String description;
    private String categoryName;
    private AttachmentSummaryResponse attachment;
    private ReportStatus status;

    private Double latitude;
    private Double longitude;

    private String address;

    private String createdByName;
    private UUID createdByUserId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ReportDetailResponse(
            UUID id,
            String title,
            String description,
            String categoryName,
            ReportStatus status,
            Double latitude,
            Double longitude,
            String address,
            String createdByName,
            UUID createdByUserId,
            LocalDateTime createdAt,
            LocalDateTime updatedAt

    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.categoryName = categoryName;
        this.status = status;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.createdByName = createdByName;
        this.createdByUserId = createdByUserId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}