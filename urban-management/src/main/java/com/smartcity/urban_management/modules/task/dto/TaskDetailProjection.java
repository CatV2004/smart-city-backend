package com.smartcity.urban_management.modules.task.dto;

import com.smartcity.urban_management.modules.report.entity.ReportStatus;
import com.smartcity.urban_management.modules.task.entity.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public interface TaskDetailProjection {

    // ===== TASK =====
    UUID getId();
    TaskStatus getStatus();
    String getNote();

    LocalDateTime getAssignedAt();
    LocalDateTime getStartedAt();
    LocalDateTime getCompletedAt();

    UUID getAssignedUserId();
    String getAssignedUserName();

    UUID getDepartmentOfficeId();
    String getDepartmentOfficeName();

    // ===== REPORT =====
    UUID getReportId();
    String getReportTitle();
    String getReportDescription();
    String getCategoryName();

    Double getLatitude();
    Double getLongitude();
    String getAddress();

    String getCreatedByName();
    UUID getCreatedByUserId();

    LocalDateTime getReportCreatedAt();
    LocalDateTime getReportUpdatedAt();

    String getApprovedByName();
    ReportStatus getReportStatus();
}
