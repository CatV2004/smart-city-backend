package com.smartcity.urban_management.modules.report.event;

import com.smartcity.urban_management.modules.report.entity.ReportStatus;

import java.util.UUID;

public record ReportStatusChangedEvent(
        UUID reportId,
        ReportStatus oldStatus,
        ReportStatus newStatus
) {}