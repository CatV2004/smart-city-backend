package com.smartcity.urban_management.security.config;

import com.smartcity.urban_management.security.handler.AccessDeniedHandlerImpl;
import com.smartcity.urban_management.security.handler.AuthEntryPoint;
import com.smartcity.urban_management.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;
    private final AuthEntryPoint authEntryPoint;
    private final AccessDeniedHandlerImpl accessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // ===== Stateless API =====
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // ===== Exception handling =====
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                )

                // ===== Authorization =====
                .authorizeHttpRequests(auth -> auth

                        // ---------- PUBLIC ----------
                        .requestMatchers(
                                // auth
                                "/api/v1/auth/**",

                                // actuator
                                "/actuator/health",

                                // swagger
                                "/swagger",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/api-docs/**",

                                // static resources
                                "/favicon.ico",
                                "/error"
                        ).permitAll()

                        /// ---------- ADMIN ONLY ----------
                        .requestMatchers("/api/v1/admin/**")
                        .hasRole("ADMIN")

                        // ---------- AUTHENTICATED USER ----------
                        .requestMatchers("/api/**")
                        .authenticated()

                        .anyRequest().denyAll()
                )

                // ===== JWT FILTER =====
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
