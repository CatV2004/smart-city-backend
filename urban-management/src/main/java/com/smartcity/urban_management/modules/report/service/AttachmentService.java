package com.smartcity.urban_management.modules.report.service;

import com.smartcity.urban_management.modules.report.dto.summary.AttachmentSummaryResponse;
import com.smartcity.urban_management.modules.report.dto.AttachmentCreateRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface AttachmentService {

    AttachmentSummaryResponse create(AttachmentCreateRequest request);

    List<AttachmentSummaryResponse> findByReport(UUID reportId);

    List<AttachmentSummaryResponse> upload(UUID reportId, List<MultipartFile> files);

}