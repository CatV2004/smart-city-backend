package com.smartcity.urban_management.modules.dashboard.citizen.dto;

import com.smartcity.urban_management.modules.report.dto.ReportDisplayStatus;
import com.smartcity.urban_management.modules.report.entity.ReportStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class RecentReportDto {

    private UUID id;
    private String title;
    private String description;
    private String categoryName;
    private ReportDisplayStatus status;

    private String address;

    private String createdByName;
    private UUID createdByUserId;

    private LocalDateTime createdAt;

}
