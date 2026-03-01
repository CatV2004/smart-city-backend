package com.smartcity.urban_management.modules.user.dto;

import lombok.Data;

@Data
public class LoginRequest {

    private String identifier;
    private String password;
}