package com.smartcity.urban_management.modules.dashboard.admin.dto.response;

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
public class PriorityReportResponse {
    private UUID id;
    private String title;
    private String status;
    private Double confidence;
    private String priority;
    private String address;
    private LocalDateTime createdAt;
    private String createdByName;
}
