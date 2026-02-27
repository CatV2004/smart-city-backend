package com.smartcity.urban_management.modules.report.controller;

import com.smartcity.urban_management.modules.report.dto.CreateReportRequest;
import com.smartcity.urban_management.modules.report.dto.ReportResponse;
import com.smartcity.urban_management.modules.report.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Reports", description = "Report management APIs")
@RestController@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @Operation(summary = "Create new report")
    @PostMapping
    public ReportResponse create(@RequestBody CreateReportRequest request) {

        // TODO: lấy từ SecurityContext sau
        UUID mockUserId = UUID.randomUUID();

        return reportService.create(request, mockUserId);
    }

    @GetMapping
    public List<ReportResponse> getAll() {
        return reportService.getAll();
    }
}