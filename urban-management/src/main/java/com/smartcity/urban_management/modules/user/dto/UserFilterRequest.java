package com.smartcity.urban_management.modules.user.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UserFilterRequest {

    private String keyword;
    private Boolean active;
    private UUID departmentId;
    private Long roleId;
}