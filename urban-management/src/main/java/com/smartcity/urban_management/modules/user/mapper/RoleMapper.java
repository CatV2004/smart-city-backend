package com.smartcity.urban_management.modules.user.mapper;

import com.smartcity.urban_management.modules.user.dto.RoleResponse;
import com.smartcity.urban_management.modules.user.entity.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {
    public RoleResponse toRoleResponse(Role role) {
        if (role == null) return null;

        return RoleResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }
}
