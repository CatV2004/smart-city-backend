package com.smartcity.urban_management.modules.dashboard.admin.service;


import com.smartcity.urban_management.modules.dashboard.admin.dto.response.DashboardStatisticsResponse;
import com.smartcity.urban_management.modules.dashboard.admin.dto.response.PriorityReportResponse;
import com.smartcity.urban_management.modules.dashboard.admin.dto.response.ResolvedReportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final DashboardStatisticsService statisticsService;
    private final PriorityReportService priorityReportService;
    private final ResolvedReportService resolvedReportService;

    public DashboardStatisticsResponse getDashboardStatistics() {
        return statisticsService.getDashboardStatistics();
    }

    public Page<PriorityReportResponse> getPriorityReports(Pageable pageable) {
        return priorityReportService.getPriorityReports(pageable);
    }

    public Page<ResolvedReportResponse> getResolvedReports(Pageable pageable) {
        return resolvedReportService.getResolvedReports(pageable);
    }
}