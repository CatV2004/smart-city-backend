package com.smartcity.urban_management.modules.report.mapper;

import com.smartcity.urban_management.modules.report.dto.summary.AttachmentSummaryResponse;
import com.smartcity.urban_management.modules.report.entity.Attachment;
import lombok.experimental.UtilityClass;
import org.springframework.stereotype.Component;

@UtilityClass
public class AttachmentMapper {

    public AttachmentSummaryResponse toResponse(Attachment attachment) {
        return AttachmentSummaryResponse.builder()
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