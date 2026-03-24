package com.smartcity.urban_management.modules.dashboard.citizen.mapper;

import com.smartcity.urban_management.modules.dashboard.citizen.dto.RecentReportDto;
import com.smartcity.urban_management.modules.report.dto.summary.ReportCitizenSummaryResponse;
import org.springframework.stereotype.Component;

@Component
public class DashboardMapper {

    public RecentReportDto toRecentReportDto(ReportCitizenSummaryResponse r) {
        return RecentReportDto.builder()
                .id(r.getId())
                .title(r.getTitle())
                .description(r.getDescription())
                .categoryName(r.getCategoryName())
                .status(r.getStatus())
                .address(r.getAddress())
                .createdByName(r.getCreatedByName())
                .createdByUserId(r.getCreatedByUserId())
                .createdAt(r.getCreatedAt())
                .build();
    }
}