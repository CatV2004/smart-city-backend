package com.smartcity.urban_management.modules.notification.service;

import com.smartcity.urban_management.modules.notification.dto.NotificationResponse;
import com.smartcity.urban_management.shared.pagination.PageRequestDto;
import com.smartcity.urban_management.shared.pagination.PageResponse;

import java.util.UUID;

public interface NotificationService {

    PageResponse<NotificationResponse> getMyNotifications(
            PageRequestDto request,
            UUID userId,
            Boolean isRead
    );

    void markAsRead(String notificationId);

    long countUnread(UUID userId);

    void createReportCreatedNotification(UUID userId, UUID reportId, String reportTitle);

    void createReportInProgressNotification(UUID userId, UUID reportId, String title);

    void createReportResolvedNotification(UUID userId, UUID reportId, String title);

    void createReportRejectedNotification(UUID userId, UUID reportId, String title);

    void notifyAdminsNewReport(UUID reportId, String title);

    void notifyAdminsAIPredicted(UUID reportId, String result, double confidence);

    void notifyAdminsTaskAssigned(UUID reportId, UUID officeId, String officeName);

    void notifyAdminsTaskStarted(UUID reportId, UUID staffId, String staffName);

    void notifyAdminsTaskCompleted(UUID reportId, UUID staffId, String staffName, String note);

    void notifyStaffNewTask(UUID userId, UUID taskId, String reportTitle);
}