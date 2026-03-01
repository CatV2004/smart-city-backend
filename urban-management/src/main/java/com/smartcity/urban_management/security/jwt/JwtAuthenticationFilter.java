//package com.smartcity.urban_management.security.jwt;
//
//import com.smartcity.urban_management.security.user.CustomUserDetailsService;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.UUID;
//
//@Component
//@RequiredArgsConstructor
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    private final JwtTokenProvider tokenProvider;
//    private final CustomUserDetailsService userDetailsService;
//
//    @Override
//    protected void doFilterInternal(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            FilterChain filterChain)
//            throws ServletException, IOException {
//
//        try {
//
//            String header = request.getHeader("Authorization");
//
//            if (header != null && header.startsWith("Bearer ")) {
//
//                String token = header.substring(7);
//
//                if (SecurityContextHolder.getContext().getAuthentication() == null
//                        && tokenProvider.validate(token)) {
//
//                    UUID userId = tokenProvider.getUserId(token);
//
//                    UserDetails userDetails =
//                            userDetailsService.loadUserByUserId(userId);
//
//                    UsernamePasswordAuthenticationToken authentication =
//                            new UsernamePasswordAuthenticationToken(
//                                    userDetails,
//                                    null,
//                                    userDetails.getAuthorities());
//
//                    authentication.setDetails(
//                            new WebAuthenticationDetailsSource()
//                                    .buildDetails(request)
//                    );
//
//                    SecurityContextHolder.getContext()
//                            .setAuthentication(authentication);
//                }
//            }
//
//        } catch (Exception ex) {
//            // token lỗi = anonymous user
//            SecurityContextHolder.clearContext();
//        }
//
//        filterChain.doFilter(request, response);
//    }
//}

package com.smartcity.urban_management.security.jwt;

import com.smartcity.urban_management.security.user.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {
        try {

            String token = extractTokenFromCookie(request);
            if (token != null
                    && SecurityContextHolder.getContext().getAuthentication() == null
                    && tokenProvider.validate(token)) {

                UUID userId = tokenProvider.getUserId(token);

                UserDetails userDetails =
                        userDetailsService.loadUserByUserId(userId);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());

                authentication.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );


                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
            }

        } catch (Exception ex) {
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }

    private String extractTokenFromCookie(HttpServletRequest request) {

        if (request.getCookies() == null) return null;

        return Arrays.stream(request.getCookies())
                .filter(cookie -> "access_token".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
    }
}