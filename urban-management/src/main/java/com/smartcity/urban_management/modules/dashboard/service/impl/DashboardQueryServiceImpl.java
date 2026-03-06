package com.smartcity.urban_management.modules.dashboard.service.impl;

import com.smartcity.urban_management.modules.dashboard.dto.ReportSummaryDto;
import com.smartcity.urban_management.modules.dashboard.repository.DashboardRepository;
import com.smartcity.urban_management.modules.dashboard.service.DashboardQueryService;
import com.smartcity.urban_management.modules.report.dto.ReportSummaryResponse;
import com.smartcity.urban_management.modules.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public List<ReportSummaryResponse> getRecentReports(UUID citizenId) {
        return reportService.getRecentReports(
                citizenId,
                3
        );
    }
}