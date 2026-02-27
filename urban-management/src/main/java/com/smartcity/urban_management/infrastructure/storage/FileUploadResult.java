package com.smartcity.urban_management.infrastructure.storage;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileUploadResult {

    private String fileUrl;
    private String publicId;
    private String fileType;
    private Integer fileSize;
    private String provider;
}