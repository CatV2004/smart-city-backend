package com.smartcity.urban_management.modules.dashboard.service.impl;

import com.smartcity.urban_management.modules.dashboard.dto.CategoryCountDto;
import com.smartcity.urban_management.modules.dashboard.dto.CitizenDashboardResponse;
import com.smartcity.urban_management.modules.dashboard.dto.RecentReportDto;
import com.smartcity.urban_management.modules.dashboard.dto.ReportSummaryDto;
import com.smartcity.urban_management.modules.dashboard.mapper.DashboardMapper;
import com.smartcity.urban_management.modules.dashboard.repository.DashboardRepository;
import com.smartcity.urban_management.modules.dashboard.service.CitizenDashboardService;
import com.smartcity.urban_management.modules.dashboard.service.DashboardQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CitizenDashboardServiceImpl implements CitizenDashboardService {

    private final DashboardQueryService queryService;
    private final DashboardRepository repository;
    private final DashboardMapper mapper;

    @Override
    public CitizenDashboardResponse getDashboard(UUID citizenId) {

        ReportSummaryDto summary =
                queryService.getSummary(citizenId);

        List<RecentReportDto> recentReports =
                queryService.getRecentReports(citizenId)
                        .stream()
                        .map(mapper::toRecentReportDto)
                        .toList();
        List<CategoryCountDto> categoryBreakdown =
                repository.getCategoryBreakdown(citizenId);

        return CitizenDashboardResponse.builder()
                .summary(summary)
                .recentReports(recentReports)
                .categoryBreakdown(categoryBreakdown)
                .build();
    }
}
