package com.smartcity.urban_management.modules.user.service.impl;

import com.smartcity.urban_management.infrastructure.redis.cache.UserCacheService;
import com.smartcity.urban_management.modules.department.entity.Department;
import com.smartcity.urban_management.modules.department.repository.DepartmentRepository;
import com.smartcity.urban_management.modules.user.entity.Role;
import com.smartcity.urban_management.modules.user.entity.RoleName;
import com.smartcity.urban_management.modules.user.entity.User;
import com.smartcity.urban_management.modules.user.pagination.UserSortField;
import com.smartcity.urban_management.shared.exception.AppException;
import com.smartcity.urban_management.shared.exception.ErrorCode;
import com.smartcity.urban_management.shared.pagination.PageMapper;
import com.smartcity.urban_management.shared.pagination.PageRequestDto;
import com.smartcity.urban_management.shared.pagination.PageResponse;
import com.smartcity.urban_management.shared.pagination.PageableFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartcity.urban_management.modules.user.dto.*;
import com.smartcity.urban_management.modules.user.repository.*;
import com.smartcity.urban_management.modules.user.service.UserService;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserSortField userSortField = new UserSortField();
    private final UserCacheService userCacheService;

    @Override
    public UserDetailResponse getCurrentUser(UUID userId) {
        return userRepository.findUserResponseById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    @Transactional
    public CreateUserResponse createUserByAdmin(CreateUserRequest request) {

        if (request == null) {
            throw new AppException(ErrorCode.BAD_REQUEST);
        }

        String email = request.getEmail().trim().toLowerCase();
        String phone = request.getPhoneNumber().trim();

        if (email.isEmpty() || phone.isEmpty() || request.getPassword() == null) {
            throw new AppException(ErrorCode.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);
        } else if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new AppException(ErrorCode.PHONE_ALREADY_EXISTS);
        }

        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

        RoleName roleName = RoleName.from(role.getName());

        if (!roleName.isAdmin() && !roleName.isStaff()) {
            throw new AppException(ErrorCode.INVALID_ROLE_ASSIGNMENT);
        }

        Department department = null;

        if (roleName.isStaff()) {

            if (request.getDepartmentId() == null) {
                throw new AppException(ErrorCode.DEPARTMENT_REQUIRED);
            }

            department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND));
        }

        User user = User.builder()
                .email(email)
                .phoneNumber(phone)
                .fullName(request.getFullName().trim())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .isActive(Boolean.TRUE)
                .department(department)
                .build();

        userRepository.save(user);

        userCacheService.evictAllUserPages();

        if (department != null) {
            userCacheService.evictDepartmentUsers(department.getId());
        }

        return new CreateUserResponse(user.getId());
    }

    @Override
    public PageResponse<UserSummaryResponse> getUsersByDepartment(
            UUID departmentId,
            PageRequestDto request
    ) {

        Pageable pageable = PageableFactory.from(request, userSortField);

        Page<UserSummaryResponse> pageResult =
                userRepository.findByDepartment(departmentId, pageable);

        return PageMapper.toResponse(pageResult);
    }

    @Override
    public PageResponse<UserSummaryResponse> getAll(
            UserFilterRequest filter,
            PageRequestDto request
    ) {
        int page = request.getPage();
        int size = request.getSize();
        String sort = request.getSort();


        // ===== BUILD FILTER KEY =====
        String filterKey = buildFilterKey(filter);

        // ===== CACHE =====
        Optional<PageResponse<UserSummaryResponse>> cached =
                userCacheService.getUserPage(page, size, sort, filterKey);

        if (cached.isPresent()) {
            return cached.get();
        }

        Pageable pageable = PageableFactory.from(request, userSortField);

        // ===== CLEAN KEYWORD =====
        String keyword = filter.getKeyword();
        if (keyword != null) {
            keyword = keyword.trim();
            if (keyword.isEmpty()) keyword = null;
        }

        // ===== QUERY (projection luôn) =====
        Page<UserSummaryResponse> pageResult = userRepository.search(
                keyword,
                filter.getActive(),
                filter.getDepartmentId(),
                filter.getRoleId(),
                pageable
        );

        PageResponse<UserSummaryResponse> response =
                PageMapper.toResponse(pageResult);

        // ===== CACHE =====
        userCacheService.cacheUserPage(page, size, sort, filterKey, response);

        return response;
    }

    private String buildFilterKey(UserFilterRequest filter) {

        return String.format(
                "keyword:%s|active:%s|dept:%s|role:%s",
                safe(filter.getKeyword()),
                safe(filter.getActive()),
                safe(filter.getDepartmentId()),
                safe(filter.getRoleId())
        );
    }

    private String safe(Object o) {
        return o == null ? "" : o.toString();
    }

}