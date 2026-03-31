package com.smartcity.urban_management.modules.task.dto;

import com.smartcity.urban_management.modules.task.entity.TaskStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class TaskFilterRequest {
    private UUID departmentOfficeId;
    private String keyword;
    private TaskStatus status;
}