package com.smartcity.urban_management.infrastructure.kafka.message;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class ReportCreatedMessage {

    private UUID reportId;
    private UUID userId;
    private String title;
    private String category;
    private UUID createdBy;
    private Instant createdAt;
}