package com.smartcity.urban_management.modules.report.service;

import com.smartcity.urban_management.modules.report.dto.AttachmentSummaryResponse;
import com.smartcity.urban_management.modules.report.dto.AttachmentCreateRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface AttachmentService {

    AttachmentSummaryResponse create(AttachmentCreateRequest request);

    List<AttachmentSummaryResponse> findByReport(UUID reportId);

    AttachmentSummaryResponse upload(UUID reportId, MultipartFile file);

}