package com.smartcity.urban_management.modules.dashboard.citizen.service.impl;

import com.smartcity.urban_management.modules.dashboard.citizen.dto.CategoryCountDto;
import com.smartcity.urban_management.modules.dashboard.citizen.dto.CitizenDashboardResponse;
import com.smartcity.urban_management.modules.dashboard.citizen.dto.RecentReportDto;
import com.smartcity.urban_management.modules.dashboard.citizen.dto.ReportSummaryDto;
import com.smartcity.urban_management.modules.dashboard.citizen.mapper.DashboardMapper;
import com.smartcity.urban_management.modules.dashboard.citizen.repository.DashboardRepository;
import com.smartcity.urban_management.modules.dashboard.citizen.service.CitizenDashboardService;
import com.smartcity.urban_management.modules.dashboard.citizen.service.DashboardQueryService;
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
                        .getRecentReportData()
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
