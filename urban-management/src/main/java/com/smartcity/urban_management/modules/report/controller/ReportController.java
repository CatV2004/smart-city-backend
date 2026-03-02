package com.smartcity.urban_management.modules.report.controller;

import com.smartcity.urban_management.modules.report.dto.ReportCreateRequest;
import com.smartcity.urban_management.modules.report.dto.ReportDetailResponse;
import com.smartcity.urban_management.modules.report.dto.ReportSummaryResponse;
import com.smartcity.urban_management.modules.report.dto.UpdateReportStatusRequest;
import com.smartcity.urban_management.modules.report.service.ReportService;
import com.smartcity.urban_management.security.user.CustomUserDetails;
import com.smartcity.urban_management.shared.pagination.PageRequestDto;
import com.smartcity.urban_management.shared.pagination.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Reports", description = "Report management APIs")
@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @Operation(summary = "Create new report")
    @PostMapping
    public ReportSummaryResponse create(
            @RequestBody ReportCreateRequest request,
            @AuthenticationPrincipal CustomUserDetails user
    ) {

        return reportService.create(request, user.getId());
    }

    @GetMapping
    public PageResponse<ReportSummaryResponse> getAll(
            @ModelAttribute PageRequestDto request
    ) {
        return reportService.findAll(request);
    }

    @Operation(summary = "Get report details by ID")
    @GetMapping("/{reportId}")
    public ReportDetailResponse getById(@PathVariable String reportId) {
        return reportService.findById(reportId);
    }

    @Operation(summary = "Admin update report status")
    @PatchMapping("/{id}/status")
    public ReportDetailResponse updateStatus(
            @PathVariable UUID id,
            @RequestBody UpdateReportStatusRequest request,
            @AuthenticationPrincipal CustomUserDetails admin
    ) {
        return reportService.updateStatus(id, request, admin.getId());
    }

    @Operation(summary = "Delete report")
    @DeleteMapping("/{id}")
    @PreAuthorize("@reportAuth.canDelete(#id, authentication)")
    public void delete(@PathVariable UUID id) {
        reportService.softDeleteReport(id);
    }
}