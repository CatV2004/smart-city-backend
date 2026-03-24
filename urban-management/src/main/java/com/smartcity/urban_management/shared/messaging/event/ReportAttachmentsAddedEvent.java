package com.smartcity.urban_management.shared.messaging.event;

import java.util.List;
import java.util.UUID;

public record ReportAttachmentsAddedEvent(
        UUID reportId,
        List<String> attachmentUrls
) {}
