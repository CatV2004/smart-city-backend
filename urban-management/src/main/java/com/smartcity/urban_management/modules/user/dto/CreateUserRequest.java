package com.smartcity.urban_management.modules.user.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateUserRequest {

    private String email;
    private String phoneNumber;
    private String password;
    private String fullName;

    private Long roleId;
    private UUID departmentId;
    private UUID officeId;
}
