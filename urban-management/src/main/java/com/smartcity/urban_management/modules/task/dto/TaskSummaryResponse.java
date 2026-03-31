package com.smartcity.urban_management.modules.task.dto;

import com.smartcity.urban_management.modules.task.entity.TaskStatus;
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
public class TaskSummaryResponse {
    private UUID id;
    private TaskStatus status;

    private LocalDateTime assignedAt;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;

    private UUID reportId;

    private UUID assignedUserId;
    private String assignedUserName;
}