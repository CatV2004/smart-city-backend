package com.smartcity.urban_management.shared.messaging.event;

import com.smartcity.urban_management.modules.report.entity.ReportStatus;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportStatusChangedEvent {
    UUID reportId;
    UUID userId;
    String title;
    ReportStatus oldStatus;
    ReportStatus newStatus;
    String changedBy;
}