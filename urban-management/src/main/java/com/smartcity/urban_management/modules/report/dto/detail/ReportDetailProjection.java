package com.smartcity.urban_management.modules.report.dto.detail;

import com.smartcity.urban_management.modules.report.entity.ReportStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ReportDetailProjection {

    UUID getId();
    String getTitle();
    String getDescription();

    String getUserCategoryName();
    String getAiCategoryName();
    String getFinalCategoryName();

    Double getAiConfidence();

    ReportStatus getStatus();

    Double getLatitude();
    Double getLongitude();

    String getAddress();

    String getCreatedByName();
    UUID getCreatedByUserId();

    String getApprovedByName();
    UUID getApprovedById();

    LocalDateTime getCreatedAt();
    LocalDateTime getUpdatedAt();
}