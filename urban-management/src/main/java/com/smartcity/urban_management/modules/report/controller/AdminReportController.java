package com.smartcity.urban_management.modules.report.controller;

import com.smartcity.urban_management.modules.report.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/reports")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminReportController {

    private final ReportService reportService;

    @Operation(summary = "Hard delete report")
    @DeleteMapping("/{id}/hard")
    public void hardDelete(@PathVariable UUID id) {
        reportService.purgeDeletedReport(id);
    }
}