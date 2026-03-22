package com.smartcity.urban_management.modules.report.dto.summary;

import com.smartcity.urban_management.modules.report.entity.Priority;
import com.smartcity.urban_management.modules.report.entity.ReportStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ReportSummaryProjection {

    UUID getId();

    String getTitle();

    String getDescription();

    String getUserCategoryName();

    String getAiCategoryName();

    String getFinalCategoryName();

    Double getAiConfidence();

    Priority getPriority();

    ReportStatus getStatus();

    Double getLatitude();

    Double getLongitude();

    String getAddress();

    String getCreatedByName();

    UUID getCreatedByUserId();

    LocalDateTime getCreatedAt();
}