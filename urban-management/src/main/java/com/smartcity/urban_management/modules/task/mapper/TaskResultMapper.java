package com.smartcity.urban_management.modules.task.mapper;

import com.smartcity.urban_management.modules.report.dto.ReportResultDto;
import com.smartcity.urban_management.modules.task.dto.EvidenceDto;
import com.smartcity.urban_management.modules.task.entity.Task;
import com.smartcity.urban_management.modules.task.entity.TaskEvidence;
import com.smartcity.urban_management.modules.task.entity.TaskStatus;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class TaskResultMapper {

    public ReportResultDto toResult(Task task, List<TaskEvidence> evidences) {

        if (task == null || task.getStatus() != TaskStatus.COMPLETED) {
            return null;
        }

        if (evidences == null || evidences.isEmpty()) {
            return null;
        }

        return ReportResultDto.builder()
                .completedAt(task.getCompletedAt())
                .note(task.getNote())
                .evidences(
                        evidences.stream()
                                .map(e -> EvidenceDto.builder()
                                        .fileUrl(e.getFileUrl())
                                        .fileName(e.getFileName())
                                        .createdAt(e.getCreatedAt())
                                        .build()
                                )
                                .toList()
                )
                .build();
    }
}
