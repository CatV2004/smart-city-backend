package com.smartcity.urban_management.modules.task.messaging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskAssignedMessage {

    private UUID taskId;
    private UUID reportId;
    private UUID officeId;
    private String officeName;
    private UUID assignedUserId;
    private String reportTitle;
}
