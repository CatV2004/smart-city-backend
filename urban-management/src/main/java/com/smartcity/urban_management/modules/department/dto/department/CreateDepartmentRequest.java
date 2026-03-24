package com.smartcity.urban_management.modules.department.dto.department;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateDepartmentRequest {

    @NotBlank(message = "Department name must not be blank")
    @Size(max = 100, message = "Department name must not exceed 100 characters")
    private String name;

    @NotBlank(message = "Department code must not be blank")
    @Size(max = 50, message = "Department code must not exceed 50 characters")
    private String code;

    private String description;
}