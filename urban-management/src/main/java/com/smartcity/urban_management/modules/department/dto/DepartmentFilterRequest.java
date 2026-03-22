package com.smartcity.urban_management.modules.department.dto;

import lombok.Data;

@Data
public class DepartmentFilterRequest {
    private String keyword;
    private Boolean active;
}
