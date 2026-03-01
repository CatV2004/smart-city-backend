package com.smartcity.urban_management.modules.report.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class AttachmentCreateRequest {

    private UUID reportId;

    private String fileName;
    private String fileUrl;

    private String storageProvider;
    private String publicId;

    private String fileType;
    private Integer fileSize;
}