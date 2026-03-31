package com.smartcity.urban_management.modules.dashboard.admin.mapper;

import com.smartcity.urban_management.modules.dashboard.admin.dto.response.PriorityReportResponse;
import com.smartcity.urban_management.modules.report.entity.Report;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ReportMapper {

    public PriorityReportResponse toPriorityResponse(Report report) {
        return PriorityReportResponse.builder()
                .id(report.getId())
                .title(report.getTitle())
                .status(report.getStatus().name())
                .confidence(report.getAiConfidence())
                .priority(report.getPriority() != null ? report.getPriority().name() : "MEDIUM")
                .address(report.getAddress())
                .createdAt(report.getCreatedAt())
                .createdByName(report.getCreatedBy().getFullName())
                .build();
    }
}
