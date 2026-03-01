package com.smartcity.urban_management.modules.user.controller;

import com.smartcity.urban_management.modules.user.dto.*;
import com.smartcity.urban_management.modules.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        LoginResponse response = authService.login(request);

        ResponseCookie cookie = ResponseCookie
                .from("access_token", response.getAccessToken())
                .httpOnly(true)
                .secure(false)        // production = true
                .path("/")
                .maxAge(response.getExpiresIn())
                .sameSite("Strict")  // hoặc "None" nếu khác domain
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(response);
    }

    @PostMapping("/register")
    public void register(@RequestBody RegisterRequest request) {
        authService.register(request);
    }
}