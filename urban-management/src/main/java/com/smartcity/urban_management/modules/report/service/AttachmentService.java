package com.smartcity.urban_management.modules.report.service;

import com.smartcity.urban_management.modules.report.dto.AttachmentResponse;
import com.smartcity.urban_management.modules.report.dto.CreateAttachmentRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface AttachmentService {

    AttachmentResponse create(CreateAttachmentRequest request);

    List<AttachmentResponse> getByReport(UUID reportId);

    AttachmentResponse upload(UUID reportId, MultipartFile file);

}