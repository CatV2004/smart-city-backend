package com.smartcity.urban_management.modules.report.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentSummaryResponse {

    private UUID id;
    private UUID reportId;

    private String fileName;
    private String fileUrl;

    private String storageProvider;
    private String publicId;

    private String fileType;
    private Integer fileSize;

    private LocalDateTime createdAt;
}