package com.smartcity.urban_management.modules.report.controller;

import com.smartcity.urban_management.modules.report.dto.*;
import com.smartcity.urban_management.modules.report.dto.detail.ReportCitizenDetailResponse;
import com.smartcity.urban_management.modules.report.dto.summary.ReportCitizenSummaryResponse;
import com.smartcity.urban_management.modules.report.entity.ReportStatus;
import com.smartcity.urban_management.modules.report.service.ReportService;
import com.smartcity.urban_management.security.user.CustomUserDetails;
import com.smartcity.urban_management.shared.pagination.PageRequestDto;
import com.smartcity.urban_management.shared.pagination.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@Tag(name = "Reports", description = "Report management APIs")
@RestController
@RequestMapping("/api/v1/citizen/reports")
@RequiredArgsConstructor
public class CitizenReportController {

    private final ReportService reportService;

    @Operation(summary = "Create new report")
    @PostMapping
    public CreateReportResponse create(
            @RequestBody ReportCreateRequest request,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return reportService.create(request, user.getId());
    }

    @Operation(summary = "Get all reports of the authenticated user")
    @GetMapping("/my")
    public PageResponse<ReportCitizenSummaryResponse> getMyReports(
            @RequestParam(required = false) ReportDisplayStatus displayStatus,
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) String keyword,

            @AuthenticationPrincipal CustomUserDetails user,
            @ModelAttribute PageRequestDto request
    ) {
        ReportFilterRequest filter = new ReportFilterRequest();
        filter.setCategoryId(categoryId);
        filter.setKeyword(keyword);
        filter.setDisplayStatus(displayStatus);

        return reportService.findByUserId(user.getId(), filter, request);
    }

    @Operation(summary = "Get report details by ID")
    @GetMapping("/{reportId}")
    public ReportCitizenDetailResponse getById(@PathVariable UUID reportId) {
        return reportService.getCitizenDetail(reportId);
    }

    @Operation(summary = "Delete report")
    @DeleteMapping("/{id}")
    @PreAuthorize("@reportAuth.canDelete(#id, authentication)")
    public void delete(
            @PathVariable UUID id,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        reportService.softDeleteReport(id, user.getId());
    }
}