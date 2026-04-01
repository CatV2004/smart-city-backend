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
public class TaskStartedMessage {

    private UUID taskId;
    private UUID reportId;
    private UUID userId;
    private String userName;
}
