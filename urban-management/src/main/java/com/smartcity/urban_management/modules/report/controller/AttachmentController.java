package com.smartcity.urban_management.modules.report.controller;

import com.smartcity.urban_management.modules.report.dto.AttachmentSummaryResponse;
import com.smartcity.urban_management.modules.report.dto.AttachmentCreateRequest;
import com.smartcity.urban_management.modules.report.service.AttachmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Tag(name = "Attachments", description = "Upload and manage attachments")
@RestController
@RequestMapping("/api/v1/attachments")
@RequiredArgsConstructor
public class AttachmentController {

    private final AttachmentService attachmentService;

    @PostMapping
    public AttachmentSummaryResponse create(@RequestBody AttachmentCreateRequest request) {
        return attachmentService.create(request);
    }

    @GetMapping("/report/{reportId}")
    public List<AttachmentSummaryResponse> findByReport(@PathVariable UUID reportId) {
        return attachmentService.findByReport(reportId);
    }

    @Operation(summary = "Upload attachments")
    @PostMapping(
            value = "/upload/{reportId}",
            consumes = "multipart/form-data"
    )
    public List<AttachmentSummaryResponse> upload(
            @PathVariable UUID reportId,
            @RequestParam("files") List<MultipartFile> files
    ) {
        return attachmentService.upload(reportId, files);
    }
}