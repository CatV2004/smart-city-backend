package com.smartcity.urban_management.modules.dashboard.admin.dto.response;

import com.smartcity.urban_management.modules.report.entity.ReportStatus;
import com.smartcity.urban_management.modules.task.entity.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ResolvedReportProjection {

    UUID getReportId();
    String getTitle();
    ReportStatus getStatus();
    LocalDateTime getCreatedAt();

    UUID getTaskId();
    TaskStatus getTaskStatus();
    String getAssignedUserName();
    LocalDateTime getCompletedAt();
}
