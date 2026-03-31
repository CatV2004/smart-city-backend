package com.smartcity.urban_management.modules.task.dto;

import com.smartcity.urban_management.modules.task.entity.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public interface TaskProjection {

    UUID getId();

    TaskStatus getStatus();

    LocalDateTime getAssignedAt();
    LocalDateTime getStartedAt();
    LocalDateTime getCompletedAt();

    UUID getReportId();

    UUID getAssignedUserId();
    String getAssignedUserName();
}
