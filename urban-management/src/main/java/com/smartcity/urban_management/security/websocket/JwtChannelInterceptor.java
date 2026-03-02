package com.smartcity.urban_management.security.websocket;


import com.smartcity.urban_management.security.jwt.JwtTokenProvider;
import com.smartcity.urban_management.security.user.CustomUserDetailsService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtChannelInterceptor implements ChannelInterceptor {

    private final JwtTokenProvider tokenProvider;
    private final CustomUserDetailsService userDetailsService;

    @Override
    public Message<?> preSend(
            Message<?> message,
            MessageChannel channel
    ) {

        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(
                        message,
                        StompHeaderAccessor.class
                );

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {

            // lấy HTTP handshake request
            ServletServerHttpRequest request =
                    (ServletServerHttpRequest)
                            accessor.getSessionAttributes()
                                    .get("HTTP_REQUEST");

            if (request != null) {

                HttpServletRequest servletRequest =
                        request.getServletRequest();

                String token = extractTokenFromCookie(servletRequest);

                if (token != null && tokenProvider.validate(token)) {

                    UUID userId = tokenProvider.getUserId(token);

                    UserDetails userDetails =
                            userDetailsService.loadUserByUserId(userId);

                    Authentication authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    accessor.setUser(authentication);
                }
            }
        }

        return message;
    }

    private String extractTokenFromCookie(HttpServletRequest request) {

        if (request.getCookies() == null) return null;

        return Arrays.stream(request.getCookies())
                .filter(c -> "access_token".equals(c.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
    }
}