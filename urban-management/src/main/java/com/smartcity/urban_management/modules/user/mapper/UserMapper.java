package com.smartcity.urban_management.modules.user.mapper;

import com.smartcity.urban_management.modules.user.dto.RoleResponse;
import com.smartcity.urban_management.modules.user.dto.UserResponse;
import com.smartcity.urban_management.modules.user.entity.Role;
import com.smartcity.urban_management.modules.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final RoleMapper roleMapper;

    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .phoneNumber(user.getPhoneNumber())
                .role(roleMapper.toRoleResponse(user.getRole()))
                .build();
    }
}
