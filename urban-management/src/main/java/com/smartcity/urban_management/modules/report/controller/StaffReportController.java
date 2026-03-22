package com.smartcity.urban_management.modules.report.controller;

import com.smartcity.urban_management.modules.report.dto.detail.ReportStaffDetailResponse;
import com.smartcity.urban_management.modules.report.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/staff/reports")
@RequiredArgsConstructor
@PreAuthorize("hasRole('STAFF')")
public class StaffReportController {

    private final ReportService reportService;

    @Operation(summary = "Get report details by ID")
    @GetMapping("/{reportId}")
    public ReportStaffDetailResponse getById(@PathVariable UUID reportId) {
        return reportService.getStaffDetail(reportId);
    }
}
