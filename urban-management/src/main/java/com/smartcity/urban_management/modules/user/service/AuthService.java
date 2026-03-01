package com.smartcity.urban_management.modules.user.service;

import com.smartcity.urban_management.modules.user.dto.*;

public interface AuthService {

    LoginResponse login(LoginRequest request);

    void register(RegisterRequest request);
}