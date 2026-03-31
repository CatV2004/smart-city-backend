package com.smartcity.urban_management.modules.task.mapper;

import com.smartcity.urban_management.modules.report.dto.detail.ReportStaffDetailResponse;
import com.smartcity.urban_management.modules.task.dto.EvidenceDto;
import com.smartcity.urban_management.modules.task.dto.TaskDetailProjection;
import com.smartcity.urban_management.modules.task.dto.TaskDetailResponse;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class TaskDetailMapper {

    public TaskDetailResponse fromProjection(
            TaskDetailProjection p,
            ReportStaffDetailResponse report,
            List<EvidenceDto> evidences
    ) {

        return TaskDetailResponse.builder()
                .id(p.getId())
                .status(p.getStatus())
                .note(p.getNote())
                .assignedAt(p.getAssignedAt())
                .startedAt(p.getStartedAt())
                .completedAt(p.getCompletedAt())
                .assignedUserId(p.getAssignedUserId())
                .assignedUserName(p.getAssignedUserName())
                .departmentOfficeId(p.getDepartmentOfficeId())
                .departmentOfficeName(p.getDepartmentOfficeName())
                .report(report)
                .evidences(evidences)
                .build();
    }
}