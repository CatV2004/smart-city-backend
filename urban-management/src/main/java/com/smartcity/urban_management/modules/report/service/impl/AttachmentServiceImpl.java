package com.smartcity.urban_management.modules.report.service.impl;

import com.smartcity.urban_management.infrastructure.storage.FileStorageService;
import com.smartcity.urban_management.infrastructure.storage.FileUploadResult;
import com.smartcity.urban_management.infrastructure.storage.StorageFolders;
import com.smartcity.urban_management.modules.report.dto.AttachmentResponse;
import com.smartcity.urban_management.modules.report.dto.CreateAttachmentRequest;
import com.smartcity.urban_management.modules.report.entity.Attachment;
import com.smartcity.urban_management.modules.report.entity.Report;
import com.smartcity.urban_management.modules.report.mapper.AttachmentMapper;
import com.smartcity.urban_management.modules.report.repository.AttachmentRepository;
import com.smartcity.urban_management.modules.report.repository.ReportRepository;
import com.smartcity.urban_management.modules.report.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final ReportRepository reportRepository;
    private final AttachmentMapper mapper;
    private final FileStorageService storageService;

    @Override
    public AttachmentResponse create(CreateAttachmentRequest request) {

        Report report = reportRepository.findById(request.getReportId())
                .orElseThrow(() -> new RuntimeException("Report not found"));

        Attachment attachment = Attachment.builder()
                .report(report)
                .fileName(request.getFileName())
                .fileUrl(request.getFileUrl())
                .storageProvider(request.getStorageProvider())
                .publicId(request.getPublicId())
                .fileType(request.getFileType())
                .fileSize(request.getFileSize())
                .createdAt(LocalDateTime.now())
                .build();

        return mapper.toResponse(
                attachmentRepository.save(attachment)
        );
    }

    @Override
    public List<AttachmentResponse> getByReport(UUID reportId) {
        return attachmentRepository.findByReportId(reportId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public AttachmentResponse upload(UUID reportId, MultipartFile file) {

        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found"));

        String folder = StorageFolders.reportFolder(reportId.toString());

        FileUploadResult uploadResult =
                storageService.upload(file, folder);

        Attachment attachment = Attachment.builder()
                .report(report)
                .fileName(file.getOriginalFilename())
                .fileUrl(uploadResult.getFileUrl())
                .publicId(uploadResult.getPublicId())
                .storageProvider(uploadResult.getProvider())
                .fileType(uploadResult.getFileType())
                .fileSize(uploadResult.getFileSize())
                .createdAt(LocalDateTime.now())
                .build();

        return mapper.toResponse(
                attachmentRepository.save(attachment)
        );
    }
}