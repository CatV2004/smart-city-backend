package com.smartcity.urban_management.modules.user.service.impl;

import com.smartcity.urban_management.infrastructure.redis.cache.UserCacheService;
import com.smartcity.urban_management.modules.department.entity.Department;
import com.smartcity.urban_management.modules.department.entity.DepartmentOffice;
import com.smartcity.urban_management.modules.department.repository.DepartmentOfficeRepository;
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
    private final DepartmentOfficeRepository officeRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserSortField userSortField = new UserSortField();

    @Override
    public UserDetailResponse getCurrentUser(UUID userId) {
        return userRepository.findUserResponseById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public CreateUserResponse createUserByAdmin(CreateUserRequest request) {

        if (request == null) {
            throw new AppException(ErrorCode.BAD_REQUEST);
        }

        String email = request.getEmail().trim().toLowerCase();
        String phone = request.getPhoneNumber().trim();

        if (email.isEmpty() || phone.isEmpty() || request.getPassword() == null) {
            throw new AppException(ErrorCode.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(email)) {
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);
        } else if (userRepository.existsByPhoneNumber(phone)) {
            throw new AppException(ErrorCode.PHONE_ALREADY_EXISTS);
        }

        // ===== ROLE =====
        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

        RoleName roleName = RoleName.from(role.getName());
        if (!roleName.isAdmin() && !roleName.isStaff()) {
            throw new AppException(ErrorCode.INVALID_ROLE_ASSIGNMENT);
        }

        // ===== DEPARTMENT =====
        Department department = null;
        if (roleName.isStaff()) {
            if (request.getDepartmentId() == null) {
                throw new AppException(ErrorCode.DEPARTMENT_REQUIRED);
            }

            department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND));
        }

        // ===== OFFICE =====
        DepartmentOffice office = null;

        if (request.getOfficeId() != null) {
            office = officeRepository.findById(request.getOfficeId())
                    .orElseThrow(() -> new AppException(ErrorCode.OFFICE_NOT_FOUND));

            if (department != null &&
                    !office.getDepartment().getId().equals(department.getId())) {
                throw new AppException(ErrorCode.OFFICE_NOT_BELONG_TO_DEPARTMENT);
            }
        }

        if (roleName.isStaff() && office == null) {
            throw new AppException(ErrorCode.OFFICE_REQUIRED);
        }

        // ===== CREATE USER =====
        User user = User.builder()
                .email(email)
                .phoneNumber(phone)
                .fullName(request.getFullName().trim())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .isActive(Boolean.TRUE)
                .department(department)
                .office(office)
                .build();

        userRepository.save(user);

        return new CreateUserResponse(user.getId());
    }

    @Override
    public PageResponse<UserSummaryResponse> getUsersByDepartment(UUID departmentId, PageRequestDto request) {
        Pageable pageable = PageableFactory.from(request, userSortField);

        Page<UserSummaryResponse> pageResult = userRepository.findByDepartment(departmentId, pageable);

        return PageMapper.toResponse(pageResult);
    }

    @Override
    public PageResponse<UserSummaryResponse> getUsersByOffice(UUID officeId, PageRequestDto request) {
        Pageable pageable = PageableFactory.from(request, userSortField);

        Page<UserSummaryResponse> pageResult = userRepository.findByOffice(officeId, pageable);

        return PageMapper.toResponse(pageResult);
    }

    @Override
    public PageResponse<UserSummaryResponse> getAll(UserFilterRequest filter, PageRequestDto request) {
        Pageable pageable = PageableFactory.from(request, userSortField);

        String keyword = filter.getKeyword();
        if (keyword != null) {
            keyword = keyword.trim();
            if (keyword.isEmpty()) keyword = null;
        }

        Page<UserSummaryResponse> pageResult = userRepository.search(
                keyword,
                filter.getActive(),
                filter.getDepartmentId(),
                filter.getRoleId(),
                pageable
        );

        return PageMapper.toResponse(pageResult);
    }

}