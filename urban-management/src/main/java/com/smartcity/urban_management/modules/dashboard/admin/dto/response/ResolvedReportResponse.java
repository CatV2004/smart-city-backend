package com.smartcity.urban_management.modules.dashboard.admin.dto.response;

import com.smartcity.urban_management.modules.report.entity.ReportStatus;
import com.smartcity.urban_management.modules.task.entity.TaskStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResolvedReportResponse {

    private UUID reportId;
    private String title;
    private ReportStatus status;
    private LocalDateTime createdAt;

    private UUID taskId;
    private TaskStatus taskStatus;
    private String assignedUserName;
    private LocalDateTime completedAt;
}