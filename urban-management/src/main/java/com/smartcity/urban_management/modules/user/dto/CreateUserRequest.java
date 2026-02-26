package com.smartcity.urban_management.modules.user.dto;

import lombok.Data;

@Data
public class CreateUserRequest {

    private String email;
    private String password;
    private String fullName;
    private String phoneNumber;
}
