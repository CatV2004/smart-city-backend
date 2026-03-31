package com.smartcity.urban_management.modules.location.dto.projection;

import java.time.LocalDateTime;
import java.util.UUID;

public interface TaskMapProjection {
    UUID getId();

    String getStatus();

    String getAssignedUserName();

    UUID getReportId();

    String getReportTitle();

    String getReportDescription();

    String getReportAddress();

    Double getReportLat();

    Double getReportLng();

    LocalDateTime getAssignedAt();

    LocalDateTime getCompletedAt();

    LocalDateTime getStartedAt();

}
