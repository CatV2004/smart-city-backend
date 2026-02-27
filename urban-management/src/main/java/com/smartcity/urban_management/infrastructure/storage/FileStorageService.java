package com.smartcity.urban_management.infrastructure.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    FileUploadResult upload(MultipartFile file, String folder);

    void delete(String publicId);
}