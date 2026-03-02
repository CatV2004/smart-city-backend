package com.smartcity.urban_management.shared.event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ReportCreatedEvent(

        UUID reportId,
        UUID userId,

        String title,
        String description,
        String category,

        double latitude,
        double longitude,
        String address,

//        List<String> attachmentUrls,

        LocalDateTime createdAt
) {}