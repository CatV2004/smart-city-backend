package com.smartcity.urban_management.modules.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailResponse {

    private UUID id;
    private String email;
    private String fullName;
    private String phoneNumber;
    private RoleResponse role;

    private String departmentCode;
}