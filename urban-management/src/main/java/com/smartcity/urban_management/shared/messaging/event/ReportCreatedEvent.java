package com.smartcity.urban_management.shared.messaging.event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ReportCreatedEvent(

        UUID reportId,
        UUID userId,

        String title,
        String description,
        UUID categoryId,

        double latitude,
        double longitude,
        String address,

//        List<String> attachmentUrls,

        LocalDateTime createdAt
) {}