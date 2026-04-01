package com.smartcity.urban_management.modules.notification.service.impl;

import com.smartcity.urban_management.modules.notification.builder.NotificationContentBuilder;
import com.smartcity.urban_management.modules.notification.dto.NotificationResponse;
import com.smartcity.urban_management.modules.notification.entity.Notification;
import com.smartcity.urban_management.modules.notification.entity.NotificationType;
import com.smartcity.urban_management.modules.notification.pagination.NotificationSortField;
import com.smartcity.urban_management.modules.notification.realtime.NotificationRealtimePublisher;
import com.smartcity.urban_management.modules.notification.repository.NotificationRepository;
import com.smartcity.urban_management.modules.notification.service.NotificationService;
import com.smartcity.urban_management.modules.user.repository.UserRepository;
import com.smartcity.urban_management.shared.pagination.PageMapper;
import com.smartcity.urban_management.shared.pagination.PageRequestDto;
import com.smartcity.urban_management.shared.pagination.PageResponse;
import com.smartcity.urban_management.shared.pagination.PageableFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository repository;
    private final NotificationSortField notificationSortField = new NotificationSortField();
    private final NotificationContentBuilder contentBuilder;
    private final NotificationRealtimePublisher realtimePublisher;
    private final UserRepository userRepository;

    @Override
    public PageResponse<NotificationResponse> getMyNotifications(
            PageRequestDto request,
            UUID userId,
            Boolean isRead
    ) {
        Pageable pageable = PageableFactory.from(request, notificationSortField);

        Page<NotificationResponse> page;

        if (isRead == null) {
            page = repository.findByUserIdAndDeletedAtIsNull(userId, pageable);
        } else {
            page = repository.findByUserIdAndDeletedAtIsNullAndIsRead(userId, isRead, pageable);
        }

        return PageMapper.toResponse(page);
    }

    @Override
    @Transactional
    public void markAsRead(String notificationId) {

        UUID id = UUID.fromString(notificationId);

        Notification notification =
                repository.findById(id)
                        .orElseThrow();

        if (!notification.getIsRead()) {
            notification.setIsRead(true);
            notification.setReadAt(LocalDateTime.now());
        }
    }

    @Override
    public long countUnread(UUID userId) {
        return repository.countByUserIdAndIsReadFalseAndDeletedAtIsNull(userId);
    }

    @Override
    @Transactional
    public void createReportCreatedNotification(
            UUID userId,
            UUID reportId,
            String title
    ) {

        log.info("Creating REPORT_CREATED notification userId={}, reportId={}",
                userId, reportId);

        boolean existed =
                repository.existsByUserIdAndReferenceIdAndType(
                        userId,
                        reportId,
                        NotificationType.REPORT_CREATED
                );

        if (existed) {
            log.warn("Notification already exists → skip");
            return;
        }

        Notification notification = Notification.builder()
                .userId(userId)
                .title("Gửi phản ánh thành công")
                .content(contentBuilder.buildReportCreated(title))
                .type(NotificationType.REPORT_CREATED)
                .referenceId(reportId)
                .isRead(false)
                .build();

        repository.save(notification);

        realtimePublisher.pushToUser(userId, notification);

        log.info("Notification saved id={}", notification.getId());
    }

    @Override
    @Transactional
    public void createReportInProgressNotification(UUID userId, UUID reportId, String title) {

        boolean exists = repository.existsByUserIdAndReferenceIdAndType(
                userId,
                reportId,
                NotificationType.REPORT_IN_PROGRESS
        );

        if (exists) {
            log.info("Skip duplicate PROCESSING notification for report {} user {}", reportId, userId);
            return;
        }

        Notification notification = Notification.builder()
                .userId(userId)
                .title("Phản ánh đang được xử lý")
                .content("Phản ánh \"" + title + "\" đang được xử lý")
                .type(NotificationType.REPORT_IN_PROGRESS)
                .referenceId(reportId)
                .isRead(false)
                .build();

        repository.save(notification);

        try {
            realtimePublisher.pushToUser(userId, notification);
        } catch (Exception e) {
            log.error("Realtime push failed", e);
        }
    }

    @Override
    @Transactional
    public void createReportResolvedNotification(UUID userId, UUID reportId, String title) {

        boolean exists = repository.existsByUserIdAndReferenceIdAndType(
                userId,
                reportId,
                NotificationType.REPORT_RESOLVED
        );

        if (exists) {
            log.info("Skip duplicate PROCESSING notification for report {} user {}", reportId, userId);
            return;
        }

        Notification notification = Notification.builder()
                .userId(userId)
                .title("Phản ánh đã được giải quyết")
                .content("Phản ánh \"" + title + "\" đã được xử lý xong")
                .type(NotificationType.REPORT_RESOLVED)
                .referenceId(reportId)
                .isRead(false)
                .build();

        repository.save(notification);
        realtimePublisher.pushToUser(userId, notification);
    }

    @Override
    @Transactional
    public void createReportRejectedNotification(UUID userId, UUID reportId, String title) {
        Notification notification = Notification.builder()
                .userId(userId)
                .title("Phản ánh bị từ chối")
                .content("Phản ánh \"" + title + "\" bị từ chối")
                .type(NotificationType.REPORT_REJECTED)
                .referenceId(reportId)
                .isRead(false)
                .build();

        repository.save(notification);
        realtimePublisher.pushToUser(userId, notification);
    }

    @Override
    @Transactional
    public void notifyAdminsNewReport(UUID reportId, String title) {

        List<UUID> adminIds = userRepository.findAllAdminIds();

        for (UUID adminId : adminIds) {

            if (repository.existsByUserIdAndReferenceIdAndType(
                    adminId,
                    reportId,
                    NotificationType.NEW_REPORT_RECEIVED
            )) {
                continue;
            }

            Notification notification = Notification.builder()
                    .userId(adminId)
                    .title("New report received")
                    .content("A new report has been submitted: \"" + title + "\"")
                    .type(NotificationType.NEW_REPORT_RECEIVED)
                    .referenceId(reportId)
                    .isRead(false)
                    .build();

            Notification saved = repository.save(notification);

            realtimePublisher.pushToUser(adminId, saved);
        }
    }

    @Override
    @Transactional
    public void notifyAdminsAIPredicted(UUID reportId, String label, double confidence) {

        List<UUID> adminIds = userRepository.findAllAdminIds();

        for (UUID adminId : adminIds) {

            if (repository.existsByUserIdAndReferenceIdAndType(
                    adminId,
                    reportId,
                    NotificationType.AI_PREDICTED
            )) {
                continue;
            }

            String content = String.format(
                    "Report ID %s has been classified as: %s (confidence: %.2f%%)",
                    reportId,
                    label,
                    confidence * 100
            );

            Notification notification = Notification.builder()
                    .userId(adminId)
                    .title("AI prediction completed")
                    .content(content)
                    .type(NotificationType.AI_PREDICTED)
                    .referenceId(reportId)
                    .isRead(false)
                    .build();

            Notification saved = repository.save(notification);
            realtimePublisher.pushToUser(adminId, saved);
        }
    }

    @Override
    @Transactional
    public void notifyAdminsTaskAssigned(UUID reportId, UUID officeId, String officeName) {
        List<UUID> adminIds = userRepository.findAllAdminIds();
        for (UUID adminId : adminIds) {
            if (repository.existsByUserIdAndReferenceIdAndType(adminId, reportId, NotificationType.NEW_REPORT_RECEIVED))
                continue;

            Notification notification = Notification.builder()
                    .userId(adminId)
                    .title("Report assigned to office")
                    .content("Report ID " + reportId + " has been assigned to office: " + officeName)
                    .type(NotificationType.NEW_REPORT_RECEIVED)
                    .referenceId(reportId)
                    .isRead(false)
                    .build();

            Notification saved = repository.save(notification);
            realtimePublisher.pushToUser(adminId, saved);
        }
    }

    @Override
    @Transactional
    public void notifyAdminsTaskStarted(UUID reportId, UUID staffId, String staffName) {
        List<UUID> adminIds = userRepository.findAllAdminIds();
        for (UUID adminId : adminIds) {
            if (repository.existsByUserIdAndReferenceIdAndType(adminId, reportId, NotificationType.TASK_STARTED)) {
                continue;
            }

            Notification notification = Notification.builder()
                    .userId(adminId)
                    .title("Task started by staff")
                    .content("Staff " + staffName + " has started working on report ID: " + reportId)
                    .type(NotificationType.TASK_STARTED)
                    .referenceId(reportId)
                    .isRead(false)
                    .build();

            Notification saved = repository.save(notification);
            realtimePublisher.pushToUser(adminId, saved);
        }
    }

    @Override
    @Transactional
    public void notifyAdminsTaskCompleted(UUID reportId, UUID staffId, String staffName, String note) {
        List<UUID> adminIds = userRepository.findAllAdminIds();
        for (UUID adminId : adminIds) {
            if (repository.existsByUserIdAndReferenceIdAndType(adminId, reportId, NotificationType.TASK_COMPLETED)) {
                continue;
            }

            Notification notification = Notification.builder()
                    .userId(adminId)
                    .title("Task completed by staff")
                    .content("Staff " + staffName + " has completed the task for report ID: " + reportId
                            + (note != null && !note.isEmpty() ? " | Note: " + note : ""))
                    .type(NotificationType.TASK_COMPLETED)
                    .referenceId(reportId)
                    .isRead(false)
                    .build();

            Notification saved = repository.save(notification);
            realtimePublisher.pushToUser(adminId, saved);
        }
    }

    @Override
    public void notifyStaffNewTask(UUID userId, UUID taskId, String reportTitle) {

        String title = "New Task Assigned";
        String content = "You have been assigned a new task: " + reportTitle;

        Notification notification = Notification.builder()
                .userId(userId)
                .title(title)
                .content(content)
                .type(NotificationType.TASK_ASSIGNED)
                .referenceId(taskId)
                .isRead(false)
                .createdAt(LocalDateTime.now())
                .build();

        repository.save(notification);

        realtimePublisher.pushToUser(userId, notification);
    }
}