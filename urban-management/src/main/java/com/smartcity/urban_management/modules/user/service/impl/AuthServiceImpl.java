package com.smartcity.urban_management.modules.user.service.impl;

import com.smartcity.urban_management.modules.user.dto.*;
import com.smartcity.urban_management.modules.user.entity.Role;
import com.smartcity.urban_management.modules.user.entity.User;
import com.smartcity.urban_management.modules.user.repository.RoleRepository;
import com.smartcity.urban_management.modules.user.repository.UserRepository;
import com.smartcity.urban_management.modules.user.service.AuthService;
import com.smartcity.urban_management.security.jwt.JwtProperties;
import com.smartcity.urban_management.security.jwt.JwtTokenProvider;
import com.smartcity.urban_management.shared.exception.AppException;
import com.smartcity.urban_management.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtProperties properties;

    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmailOrPhoneNumber(request.getIdentifier(), request.getIdentifier())
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_CREDENTIALS));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.INVALID_CREDENTIALS);
        }

        String token = jwtTokenProvider.generateToken(user.getId());

        return LoginResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .expiresIn(properties.getExpiration())
                .build();
    }

    @Override
    public void register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);
        } else if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new AppException(ErrorCode.PHONE_ALREADY_EXISTS);
        }
        Role role = roleRepository.findByName("CITIZEN")
                .orElseThrow(() -> new RuntimeException("Role not found"));

        User user = User.builder()
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .fullName(request.getFullName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();

        userRepository.save(user);
    }
}