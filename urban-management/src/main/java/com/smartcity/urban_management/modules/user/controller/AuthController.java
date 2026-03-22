package com.smartcity.urban_management.modules.user.controller;

import com.smartcity.urban_management.modules.user.dto.*;
import com.smartcity.urban_management.modules.user.service.AuthService;
import com.smartcity.urban_management.modules.user.service.UserService;
import com.smartcity.urban_management.security.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        LoginResponse response = authService.login(request);

        ResponseCookie cookie = ResponseCookie
                .from("access_token", response.getAccessToken())
                .httpOnly(true)
                .secure(false)        // production = true
                .path("/")
                .maxAge(response.getExpiresIn())
//                .sameSite("None") // hoặc "Strict" nếu cùng domain, "None" nếu cross-domain (cần secure=true)
//                .secure(true)
                .sameSite("Lax")
                .secure(false)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(response);
    }

    @PostMapping("/register")
    public void register(@RequestBody RegisterRequest request) {
        authService.register(request);
    }

    @GetMapping("/me")
    public UserDetailResponse getCurrentUser(
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return userService.getCurrentUser(user.getId());
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {

        ResponseCookie cookie = ResponseCookie
                .from("access_token", "")
                .httpOnly(true)
                .secure(false) // production = true
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }
}