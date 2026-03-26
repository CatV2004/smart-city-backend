package com.smartcity.urban_management.modules.location.dto.projection;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ReportMapProjection {

    UUID getId();
    String getTitle();
    String getDescription();
    String getStatus();
    String getPriority();
    String getAddress();

    String getCategoryName();
    Double getAiConfidence();

    Double getLat();
    Double getLng();

    LocalDateTime getCreatedAt();
    LocalDateTime getUpdatedAt();
}