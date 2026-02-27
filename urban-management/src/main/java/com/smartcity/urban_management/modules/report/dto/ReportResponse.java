package com.smartcity.urban_management.modules.report.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class ReportResponse {

    private UUID id;
    private String title;
    private String description;
    private String category;
    private String status;

    private Double latitude;
    private Double longitude;

    private String address;
    private LocalDateTime createdAt;
}