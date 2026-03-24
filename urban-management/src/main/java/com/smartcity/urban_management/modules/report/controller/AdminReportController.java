package com.smartcity.urban_management.modules.report.controller;

import com.smartcity.urban_management.modules.report.dto.*;
import com.smartcity.urban_management.modules.report.dto.detail.ReportAdminDetailResponse;
import com.smartcity.urban_management.modules.report.dto.summary.ReportAdminSummaryResponse;
import com.smartcity.urban_management.modules.report.entity.ReportStatus;
import com.smartcity.urban_management.modules.report.service.ReportService;
import com.smartcity.urban_management.security.user.CustomUserDetails;
import com.smartcity.urban_management.shared.pagination.PageRequestDto;
import com.smartcity.urban_management.shared.pagination.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/reports")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminReportController {

    private final ReportService reportService;

    @Operation(summary = "Hard delete report")
    @DeleteMapping("/{id}/hard")
    public void hardDelete(
            @PathVariable UUID id,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        reportService.purgeDeletedReport(id, user.getId());
    }

    @GetMapping
    public PageResponse<ReportAdminSummaryResponse> getAll(
            @RequestParam(required = false) Set<ReportStatus> statuses,
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) String keyword,

            @ModelAttribute PageRequestDto request
    ) {
        ReportFilterRequest filter = new ReportFilterRequest();
        filter.setStatuses(statuses);
        filter.setCategoryId(categoryId);
        filter.setKeyword(keyword);

        return reportService.findAllForAdmin(filter, request);
    }

    @Operation(summary = "Admin update report status")
    @PatchMapping("/{id}/status")
    public void updateStatus(
            @PathVariable UUID id,
            @RequestBody UpdateReportStatusRequest request,
            @AuthenticationPrincipal CustomUserDetails admin
    ) {
        reportService.adminUpdateStatus(id, request, admin.getId());
    }

    @Operation(summary = "Get report details by ID")
    @GetMapping("/{reportId}")
    public ReportAdminDetailResponse getById(@PathVariable UUID reportId) {
        return reportService.getAdminDetail(reportId);
    }

    @PatchMapping("/{reportId}/final-category")
    public void updateFinalCategory(
            @RequestBody UpdateFinalCategoryRequest finalCateRequest,
            @PathVariable UUID reportId,
            @AuthenticationPrincipal CustomUserDetails admin
    ) {
        reportService.updateFinalCategory(reportId, finalCateRequest, admin.getId());
    }
}