package com.smartcity.urban_management.modules.notification.realtime;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class NotificationRealtimePublisher {

    private final SimpMessagingTemplate messagingTemplate;

    public void pushToUser(UUID userId, Object payload) {
        System.out.println("Pushing notification to user " + userId + ": " + payload);

        messagingTemplate.convertAndSendToUser(
                userId.toString(),
                "/queue/notifications",
                payload
        );
    }
}