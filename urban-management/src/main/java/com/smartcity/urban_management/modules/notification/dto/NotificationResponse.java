package com.smartcity.urban_management.modules.notification.dto;

import com.smartcity.urban_management.modules.notification.entity.NotificationType;

import java.time.LocalDateTime;
import java.util.UUID;

public interface NotificationResponse {

    UUID getId();
    String getTitle();
    String getContent();
    NotificationType getType();
    UUID getReferenceId();
    Boolean getIsRead();
    LocalDateTime getCreatedAt();
}