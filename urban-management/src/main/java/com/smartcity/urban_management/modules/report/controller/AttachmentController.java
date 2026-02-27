package com.smartcity.urban_management.modules.report.controller;

import com.smartcity.urban_management.modules.report.dto.AttachmentResponse;
import com.smartcity.urban_management.modules.report.dto.CreateAttachmentRequest;
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
    public AttachmentResponse create(@RequestBody CreateAttachmentRequest request) {
        return attachmentService.create(request);
    }

    @GetMapping("/report/{reportId}")
    public List<AttachmentResponse> getByReport(@PathVariable UUID reportId) {
        return attachmentService.getByReport(reportId);
    }

    @Operation(summary = "Upload attachment")
    @PostMapping(value = "/upload/{reportId}",
            consumes = "multipart/form-data")
    public AttachmentResponse upload(
            @PathVariable UUID reportId,
            @RequestParam("file") MultipartFile file
    ) {
        return attachmentService.upload(reportId, file);
    }
}