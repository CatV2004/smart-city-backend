package com.smartcity.urban_management.modules.location.dto;

import com.smartcity.urban_management.modules.task.entity.TaskStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MapStaffFilterRequest {
    private List<TaskStatus> taskStatuses;
    private String keyword;
}
