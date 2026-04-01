package com.smartcity.urban_management.modules.notification.repository;

import com.smartcity.urban_management.modules.notification.dto.NotificationResponse;
import com.smartcity.urban_management.modules.notification.entity.Notification;
import com.smartcity.urban_management.modules.notification.entity.NotificationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {

    Page<NotificationResponse> findByUserIdAndDeletedAtIsNull(UUID userId, Pageable pageable);

    Page<NotificationResponse> findByUserIdAndDeletedAtIsNullAndIsRead(UUID userId, Boolean isRead, Pageable pageable);

    long countByUserIdAndIsReadFalseAndDeletedAtIsNull(UUID userId);

    boolean existsByUserIdAndReferenceIdAndType(UUID userId, UUID referenceId, NotificationType type);


}