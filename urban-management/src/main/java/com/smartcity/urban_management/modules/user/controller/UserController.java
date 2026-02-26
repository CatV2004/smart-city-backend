package com.smartcity.urban_management.modules.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.smartcity.urban_management.modules.user.dto.*;
import com.smartcity.urban_management.modules.user.service.UserService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public UserResponse register(@RequestBody CreateUserRequest request) {
        return userService.createCitizen(request);
    }
}
