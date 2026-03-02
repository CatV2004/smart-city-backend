package com.smartcity.urban_management.modules.notification.controller;

import com.smartcity.urban_management.modules.notification.dto.NotificationResponse;
import com.smartcity.urban_management.modules.notification.service.NotificationService;
import com.smartcity.urban_management.security.user.CustomUserDetails;
import com.smartcity.urban_management.shared.pagination.PageRequestDto;
import com.smartcity.urban_management.shared.pagination.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public PageResponse<NotificationResponse> getMyNotifications(
            PageRequestDto request,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return notificationService.getMyNotifications(request, user.getId());
    }

    @PatchMapping("/{id}/read")
    public void markRead(
            @PathVariable String id
    ) {
        notificationService.markAsRead(id);
    }

    @GetMapping("/unread-count")
    public long unreadCount(
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return notificationService.countUnread(user.getId());
    }
}
