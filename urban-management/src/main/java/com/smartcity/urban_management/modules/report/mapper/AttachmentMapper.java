package com.smartcity.urban_management.modules.report.mapper;

import com.smartcity.urban_management.modules.report.dto.AttachmentResponse;
import com.smartcity.urban_management.modules.report.entity.Attachment;
import org.springframework.stereotype.Component;

@Component
public class AttachmentMapper {

    public AttachmentResponse toResponse(Attachment attachment) {
        return AttachmentResponse.builder()
                .id(attachment.getId())
                .reportId(attachment.getReport().getId())
                .fileName(attachment.getFileName())
                .fileUrl(attachment.getFileUrl())
                .storageProvider(attachment.getStorageProvider())
                .publicId(attachment.getPublicId())
                .fileType(attachment.getFileType())
                .fileSize(attachment.getFileSize())
                .createdAt(attachment.getCreatedAt())
                .build();
    }
}