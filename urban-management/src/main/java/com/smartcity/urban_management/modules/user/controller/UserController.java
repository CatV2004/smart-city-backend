package com.smartcity.urban_management.modules.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.smartcity.urban_management.modules.user.dto.*;
import com.smartcity.urban_management.modules.user.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

}
