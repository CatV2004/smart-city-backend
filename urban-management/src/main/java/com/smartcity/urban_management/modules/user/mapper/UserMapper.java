package com.smartcity.urban_management.modules.user.mapper;

import com.smartcity.urban_management.modules.user.dto.UserSummaryResponse;
import com.smartcity.urban_management.modules.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserSummaryResponse toSummary(User user) {
        if (user == null) return null;

        return UserSummaryResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}