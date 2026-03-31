package com.smartcity.urban_management.modules.task.mapper;

import com.smartcity.urban_management.modules.task.dto.TaskProjection;
import com.smartcity.urban_management.modules.task.dto.TaskSummaryResponse;
import com.smartcity.urban_management.modules.task.entity.Task;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TaskSummaryMapper {

    public TaskSummaryResponse toSummary(TaskProjection p) {
        if (p == null) return null;

        return TaskSummaryResponse.builder()
                .id(p.getId())
                .status(p.getStatus())
                .assignedAt(p.getAssignedAt())
                .startedAt(p.getStartedAt())
                .completedAt(p.getCompletedAt())
                .reportId(p.getReportId())
                .assignedUserId(p.getAssignedUserId())
                .assignedUserName(p.getAssignedUserName())
                .build();
    }

    public TaskSummaryResponse toSummary(Task t) {
        if (t == null) return null;

        return TaskSummaryResponse.builder()
                .id(t.getId())
                .status(t.getStatus())
                .assignedAt(t.getAssignedAt())
                .startedAt(t.getStartedAt())
                .completedAt(t.getCompletedAt())
                .reportId(
                        t.getReport() != null ? t.getReport().getId() : null
                )
                .assignedUserId(
                        t.getAssignedUser() != null ? t.getAssignedUser().getId() : null
                )
                .assignedUserName(
                        t.getAssignedUser() != null ? t.getAssignedUser().getFullName() : null
                )
                .build();
    }
}