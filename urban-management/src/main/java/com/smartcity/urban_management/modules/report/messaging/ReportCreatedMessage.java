package com.smartcity.urban_management.modules.report.messaging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportCreatedMessage {

    UUID reportId;
    UUID userId;

    String title;
    String description;
    UUID categoryId;

    double latitude;
    double longitude;
    String address;

    LocalDateTime createdAt;
}