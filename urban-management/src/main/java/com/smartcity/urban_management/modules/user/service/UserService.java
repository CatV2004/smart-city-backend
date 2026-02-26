package com.smartcity.urban_management.modules.user.service;

import com.smartcity.urban_management.modules.user.dto.CreateUserRequest;
import com.smartcity.urban_management.modules.user.dto.UserResponse;

public interface UserService {

    UserResponse createCitizen(CreateUserRequest request);
}