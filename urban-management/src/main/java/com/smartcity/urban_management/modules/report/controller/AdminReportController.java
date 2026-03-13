package com.smartcity.urban_management.modules.report.controller;

import com.smartcity.urban_management.modules.report.dto.ReportDetailResponse;
import com.smartcity.urban_management.modules.report.dto.ReportFilterRequest;
import com.smartcity.urban_management.modules.report.dto.ReportSummaryResponse;
import com.smartcity.urban_management.modules.report.dto.UpdateReportStatusRequest;
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
    public PageResponse<ReportSummaryResponse> getAll(
            @RequestParam(required = false) ReportStatus status,
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) String keyword,

            @ModelAttribute PageRequestDto request
    ) {
        ReportFilterRequest filter = new ReportFilterRequest();
        filter.setStatus(status);
        filter.setCategoryId(categoryId);
        filter.setKeyword(keyword);

        return reportService.findAll(filter, request);
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
}