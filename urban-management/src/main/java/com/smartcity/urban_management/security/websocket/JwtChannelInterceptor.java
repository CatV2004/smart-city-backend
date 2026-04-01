package com.smartcity.urban_management.security.websocket;

import com.smartcity.urban_management.security.jwt.JwtTokenProvider;
import com.smartcity.urban_management.security.user.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
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
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor == null) return message;

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {

            try {
                String authHeader = accessor.getFirstNativeHeader("Authorization");

                System.out.println("🔐 WS Authorization: " + authHeader);

                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                    System.out.println("❌ Missing or invalid Authorization header");
                    return message;
                }

                String token = authHeader.substring(7);

                if (!tokenProvider.validate(token)) {
                    System.out.println("❌ Invalid JWT token");
                    return message;
                }

                UUID userId = tokenProvider.getUserId(token);

                UserDetails userDetails =
                        userDetailsService.loadUserByUserId(userId);

                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        ) {
                            @Override
                            public String getName() {
                                return userId.toString();
                            }
                        };

                accessor.setUser(authentication);

                System.out.println("✅ WebSocket authenticated user: " + userId);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return message;
    }
}