package com.smartcity.urban_management.modules.department.dto.department;

import lombok.Data;

@Data
public class UpdateDepartmentRequest {

    private String name;

    private String description;

    private Boolean isActive;
}