package com.smartcity.urban_management.modules.notification.service;

import com.smartcity.urban_management.modules.notification.dto.NotificationResponse;
import com.smartcity.urban_management.shared.pagination.PageRequestDto;
import com.smartcity.urban_management.shared.pagination.PageResponse;

import java.util.UUID;

public interface NotificationService {

    PageResponse<NotificationResponse> getMyNotifications(
            PageRequestDto request,
            UUID userId
    );

    void markAsRead(String notificationId);

    long countUnread(UUID userId);

    void createReportCreatedNotification(UUID userId, UUID reportId, String reportTitle);
}