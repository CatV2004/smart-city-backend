package com.smartcity.urban_management.modules.user.service;

import com.smartcity.urban_management.modules.user.dto.*;
import com.smartcity.urban_management.shared.pagination.PageRequestDto;
import com.smartcity.urban_management.shared.pagination.PageResponse;

import java.util.UUID;

public interface UserService {

    UserDetailResponse getCurrentUser(UUID userId);

    CreateUserResponse createUserByAdmin(CreateUserRequest request);

    PageResponse<UserSummaryResponse> getUsersByDepartment(UUID departmentId, PageRequestDto request);

    PageResponse<UserSummaryResponse> getUsersByOffice(UUID officeId, PageRequestDto request);

    PageResponse<UserSummaryResponse> getAll(UserFilterRequest filter, PageRequestDto request);
}