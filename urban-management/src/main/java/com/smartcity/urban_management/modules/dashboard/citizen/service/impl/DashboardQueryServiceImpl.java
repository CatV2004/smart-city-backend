package com.smartcity.urban_management.modules.dashboard.citizen.service.impl;

import com.smartcity.urban_management.modules.dashboard.citizen.dto.ReportSummaryDto;
import com.smartcity.urban_management.modules.dashboard.citizen.repository.DashboardRepository;
import com.smartcity.urban_management.modules.dashboard.citizen.service.DashboardQueryService;
import com.smartcity.urban_management.modules.report.dto.RecentReportData;
import com.smartcity.urban_management.modules.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DashboardQueryServiceImpl implements DashboardQueryService {

    private final DashboardRepository dashboardRepository;
    private final ReportService reportService;

    @Override
    public ReportSummaryDto getSummary(UUID citizenId) {
        return dashboardRepository.getCitizenSummary(citizenId);
    }

    @Override
    public RecentReportData getRecentReports(UUID citizenId) {
        return reportService.getRecentReports(
                citizenId,
                3
        );
    }
}