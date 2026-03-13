package com.smartcity.urban_management.modules.dashboard.mapper;

import com.smartcity.urban_management.modules.dashboard.dto.RecentReportDto;
import com.smartcity.urban_management.modules.report.dto.ReportSummaryResponse;
import org.springframework.stereotype.Component;

@Component
public class DashboardMapper {

    public RecentReportDto toRecentReportDto(ReportSummaryResponse r) {
        return RecentReportDto.builder()
                .id(r.getId())
                .title(r.getTitle())
                .description(r.getDescription())
                .categoryName(r.getCategoryName())
                .status(r.getStatus())
                .latitude(r.getLatitude())
                .longitude(r.getLongitude())
                .address(r.getAddress())
                .createdByName(r.getCreatedByName())
                .createdByUserId(r.getCreatedByUserId())
                .createdAt(r.getCreatedAt())
                .build();
    }
}