package com.smartcity.urban_management.shared.event;

import java.util.List;
import java.util.UUID;

public record ReportAttachmentsAddedEvent(
        UUID reportId,
        List<String> attachmentUrls
) {}
