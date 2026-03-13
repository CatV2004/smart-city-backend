package com.smartcity.urban_management.modules.report.controller;

import com.smartcity.urban_management.modules.report.dto.*;
import com.smartcity.urban_management.modules.report.entity.ReportStatus;
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

    @Operation(summary = "Get all reports of the authenticated user")
    @GetMapping("/my")
    public PageResponse<ReportSummaryResponse> getMyReports(
            @RequestParam(required = false) ReportStatus status,
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) String keyword,

            @AuthenticationPrincipal CustomUserDetails user,
            @ModelAttribute PageRequestDto request
    ) {
        ReportFilterRequest filter = new ReportFilterRequest();
        filter.setStatus(status);
        filter.setCategoryId(categoryId);
        filter.setKeyword(keyword);

        return reportService.findByUserId(user.getId(), filter, request);
    }

    @Operation(summary = "Get report details by ID")
    @GetMapping("/{reportId}")
    public ReportDetailResponse getById(@PathVariable String reportId) {
        return reportService.findById(reportId);
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