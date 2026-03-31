package com.smartcity.urban_management.modules.report.messaging;

import com.smartcity.urban_management.modules.report.entity.ReportStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportStatusChangedMessage {
    UUID reportId;
    ReportStatus oldStatus;
    ReportStatus newStatus;
    LocalDateTime changedAt;
    String changedBy;
}