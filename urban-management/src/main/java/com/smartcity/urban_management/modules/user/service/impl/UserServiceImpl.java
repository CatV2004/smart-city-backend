package com.smartcity.urban_management.modules.user.service.impl;

import com.smartcity.urban_management.modules.user.mapper.UserMapper;
import com.smartcity.urban_management.shared.exception.AppException;
import com.smartcity.urban_management.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartcity.urban_management.modules.user.dto.*;
import com.smartcity.urban_management.modules.user.entity.*;
import com.smartcity.urban_management.modules.user.repository.*;
import com.smartcity.urban_management.modules.user.service.UserService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper mapper;

    @Override
    public UserResponse getCurrentUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        return mapper.toResponse(user);
    }

}