package com.smartcity.urban_management.modules.user.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class RoleResponse {
    private Long id;
    private String name;
}