package com.smartcity.urban_management.infrastructure.storage.cloudinary;

import com.cloudinary.Cloudinary;
import com.smartcity.urban_management.infrastructure.storage.FileStorageService;
import com.smartcity.urban_management.infrastructure.storage.FileUploadResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService implements FileStorageService {

    private final Cloudinary cloudinary;

    @Override
    public FileUploadResult upload(MultipartFile file, String folder) {

        try {

            Map<String, Object> options = Map.of(
                    "folder", folder,
                    "resource_type", "auto"
            );

            Map<?, ?> result =
                    cloudinary.uploader().upload(file.getBytes(), options);

            return FileUploadResult.builder()
                    .fileUrl((String) result.get("secure_url"))
                    .publicId((String) result.get("public_id"))
                    .fileType(file.getContentType())
                    .fileSize((int) file.getSize())
                    .provider("cloudinary")
                    .build();

        } catch (Exception e) {
            throw new RuntimeException("Upload file failed", e);
        }
    }

    @Override
    public void delete(String publicId) {
        try {
            cloudinary.uploader().destroy(publicId, Map.of());
        } catch (Exception e) {
            throw new RuntimeException("Delete file failed", e);
        }
    }
}