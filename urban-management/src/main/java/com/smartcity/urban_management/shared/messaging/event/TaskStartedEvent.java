package com.smartcity.urban_management.shared.messaging.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class TaskStartedEvent {

    private UUID taskId;
    private UUID reportId;
    private UUID userId;
    private String userName;
}