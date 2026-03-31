package com.smartcity.urban_management.modules.task.dto;

import com.smartcity.urban_management.modules.report.dto.detail.ReportStaffDetailResponse;
import com.smartcity.urban_management.modules.task.entity.TaskStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDetailResponse {

    private UUID id;

    private TaskStatus status;
    private String note;

    private LocalDateTime assignedAt;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;

    private UUID assignedUserId;
    private String assignedUserName;

    private UUID departmentOfficeId;
    private String departmentOfficeName;

    private ReportStaffDetailResponse report;

    private List<EvidenceDto> evidences;
}