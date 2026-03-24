package com.smartcity.urban_management.modules.department.dto.office;

import lombok.Data;

import java.util.UUID;

@Data
public class DepartmentOfficeRequest {

    private UUID departmentId;

    private String name;

    private String address;

    private Double latitude;

    private Double longitude;

    private Boolean isActive;
}
