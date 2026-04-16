package com.smartcity.urban_management.security.websocket;

import com.smartcity.urban_management.security.jwt.JwtTokenProvider;
import jakarta.servlet.http.Cookie;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CustomHandshakeHandler extends DefaultHandshakeHandler {

    private final JwtTokenProvider tokenProvider;

    public CustomHandshakeHandler(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected Principal determineUser(
            ServerHttpRequest request,
            WebSocketHandler wsHandler,
            Map<String, Object> attributes
    ) {
        if (request instanceof ServletServerHttpRequest servletRequest) {
            Cookie[] cookies = servletRequest.getServletRequest().getCookies();

            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("access_token".equals(cookie.getName())) {
                        String token = cookie.getValue();

                        if (tokenProvider.validate(token)) {
                            UUID userId = tokenProvider.getUserId(token);
                            String role = tokenProvider.getRole(token);

                            // Create authentication object
                            List<SimpleGrantedAuthority> authorities = List.of(
                                    new SimpleGrantedAuthority("ROLE_" + role)
                            );

                            Authentication auth = new UsernamePasswordAuthenticationToken(
                                    userId.toString(),
                                    null,
                                    authorities
                            );

                            // Store in attributes for later use
                            attributes.put("userId", userId);
                            attributes.put("authentication", auth);

                            System.out.println("✅ User authenticated in handshake: " + userId);

                            return auth;
                        }
                    }
                }
            }
        }

        // Anonymous user
        System.out.println("⚠️ Anonymous WebSocket connection");
        return () -> "anonymous";
    }
}