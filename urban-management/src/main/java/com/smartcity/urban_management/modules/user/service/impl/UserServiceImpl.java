package com.smartcity.urban_management.modules.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartcity.urban_management.modules.user.dto.*;
import com.smartcity.urban_management.modules.user.entity.*;
import com.smartcity.urban_management.modules.user.repository.*;
import com.smartcity.urban_management.modules.user.service.UserService;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

}