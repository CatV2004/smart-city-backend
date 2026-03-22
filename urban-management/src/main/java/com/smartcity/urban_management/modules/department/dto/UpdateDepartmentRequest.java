package com.smartcity.urban_management.modules.department.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateDepartmentRequest {

    private String name;

    private String description;

    private Boolean isActive;
}