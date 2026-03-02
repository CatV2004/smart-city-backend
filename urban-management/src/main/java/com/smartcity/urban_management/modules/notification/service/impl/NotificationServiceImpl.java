package com.smartcity.urban_management.modules.notification.service.impl;

import com.smartcity.urban_management.modules.notification.builder.NotificationContentBuilder;
import com.smartcity.urban_management.modules.notification.dto.NotificationResponse;
import com.smartcity.urban_management.modules.notification.entity.Notification;
import com.smartcity.urban_management.modules.notification.entity.NotificationType;
import com.smartcity.urban_management.modules.notification.pagination.NotificationSortField;
import com.smartcity.urban_management.modules.notification.realtime.NotificationRealtimePublisher;
import com.smartcity.urban_management.modules.notification.repository.NotificationRepository;
import com.smartcity.urban_management.modules.notification.service.NotificationService;
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

    @Override
    public PageResponse<NotificationResponse> getMyNotifications(
            PageRequestDto request,
            UUID userId
    ) {


        Pageable pageable = PageableFactory.from(request, notificationSortField);

        Page<NotificationResponse> page =
                repository.findByUserIdAndDeletedAtIsNull(userId, pageable);

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
}