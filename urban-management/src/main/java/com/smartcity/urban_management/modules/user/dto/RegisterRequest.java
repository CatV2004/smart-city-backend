package com.smartcity.urban_management.modules.user.dto;

import lombok.Data;

@Data
public class RegisterRequest {

    private String email;
    private String phoneNumber;
    private String password;
    private String fullName;
}