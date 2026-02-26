package com.smartcity.urban_management.shared.event;

import java.util.UUID;

public record ReportCreatedEvent(
        UUID reportId,
        UUID userId,
        String title
) {}