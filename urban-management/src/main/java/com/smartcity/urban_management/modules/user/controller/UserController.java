package com.smartcity.urban_management.modules.user.controller;

import com.smartcity.urban_management.shared.pagination.PageRequestDto;
import com.smartcity.urban_management.shared.pagination.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.smartcity.urban_management.modules.user.dto.*;
import com.smartcity.urban_management.modules.user.service.UserService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public CreateUserResponse createUser(@RequestBody CreateUserRequest request) {
        return userService.createUserByAdmin(request);
    }

    @GetMapping
    public ResponseEntity<PageResponse<UserSummaryResponse>> getAllUsers(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) UUID departmentId,
            @RequestParam(required = false) Long roleId,
            @ModelAttribute PageRequestDto request
    ) {

        UserFilterRequest filter = new UserFilterRequest();
        filter.setKeyword(keyword);
        filter.setActive(active);
        filter.setDepartmentId(departmentId);
        filter.setRoleId(roleId);

        PageResponse<UserSummaryResponse> response =
                userService.getAll(filter, request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<PageResponse<UserSummaryResponse>> getUsersByDepartment(
            @PathVariable UUID departmentId,
            @ModelAttribute PageRequestDto request
    ) {
        PageResponse<UserSummaryResponse> response =
                userService.getUsersByDepartment(departmentId, request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/office/{officeId}")
    public ResponseEntity<PageResponse<UserSummaryResponse>> getUsersByOffice(
            @PathVariable UUID officeId,
            @ModelAttribute PageRequestDto request
    ) {
        PageResponse<UserSummaryResponse> response =
                userService.getUsersByOffice(officeId, request);

        return ResponseEntity.ok(response);
    }
}
