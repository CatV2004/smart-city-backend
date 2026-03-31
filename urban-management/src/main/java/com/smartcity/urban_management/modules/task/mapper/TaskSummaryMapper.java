package com.smartcity.urban_management.modules.task.mapper;

import com.smartcity.urban_management.modules.task.dto.TaskProjection;
import com.smartcity.urban_management.modules.task.dto.TaskSummaryResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TaskMapper {

    public TaskSummaryResponse toSummary(TaskProjection p) {
        if (p == null) return null;

        return TaskSummaryResponse.builder()
                .id(p.getId())
                .status(p.getStatus())
                .note(p.getNote())
                .assignedAt(p.getAssignedAt())
                .startedAt(p.getStartedAt())
                .completedAt(p.getCompletedAt())
                .reportId(p.getReportId())
                .assignedUserId(p.getAssignedUserId())
                .assignedUserName(p.getAssignedUserName())
                .build();
    }
}