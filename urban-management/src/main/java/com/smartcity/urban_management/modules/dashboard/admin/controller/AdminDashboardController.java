package com.smartcity.urban_management.modules.dashboard.admin.controller;

import com.smartcity.urban_management.modules.dashboard.admin.dto.response.DashboardStatisticsResponse;
import com.smartcity.urban_management.modules.dashboard.admin.dto.response.PriorityReportResponse;
import com.smartcity.urban_management.modules.dashboard.admin.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/dashboard")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminDashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/statistics")
    public ResponseEntity<DashboardStatisticsResponse> getStatistics() {
        return ResponseEntity.ok(dashboardService.getDashboardStatistics());
    }

    @GetMapping("/priority-reports")
    public ResponseEntity<Page<PriorityReportResponse>> getPriorityReports(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.DESC, "priority", "createdAt"));
        return ResponseEntity.ok(dashboardService.getPriorityReports(pageable));
    }
}