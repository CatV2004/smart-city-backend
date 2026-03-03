package com.smartcity.urban_management.modules.user.service;

import com.smartcity.urban_management.modules.user.dto.UserResponse;

import java.util.UUID;

public interface UserService {

    UserResponse getCurrentUser(UUID userId);

}