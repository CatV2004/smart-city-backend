package com.smartcity.urban_management.modules.report.service.impl;

import com.smartcity.urban_management.infrastructure.storage.FileStorageService;
import com.smartcity.urban_management.infrastructure.storage.FileUploadResult;
import com.smartcity.urban_management.infrastructure.storage.StorageFolders;
import com.smartcity.urban_management.modules.report.dto.summary.AttachmentSummaryResponse;
import com.smartcity.urban_management.modules.report.dto.AttachmentCreateRequest;
import com.smartcity.urban_management.modules.report.entity.Attachment;
import com.smartcity.urban_management.modules.report.entity.Report;
import com.smartcity.urban_management.modules.report.mapper.AttachmentMapper;
import com.smartcity.urban_management.modules.report.repository.AttachmentRepository;
import com.smartcity.urban_management.modules.report.repository.ReportRepository;
import com.smartcity.urban_management.modules.report.service.AttachmentService;
import com.smartcity.urban_management.shared.messaging.event.ReportAttachmentsAddedEvent;
import com.smartcity.urban_management.shared.exception.AppException;
import com.smartcity.urban_management.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final ReportRepository reportRepository;
    private final AttachmentMapper mapper;
    private final FileStorageService storageService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public AttachmentSummaryResponse create(AttachmentCreateRequest request) {

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
    public List<AttachmentSummaryResponse> findByReport(UUID reportId) {
        return attachmentRepository.findByReportId(reportId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public List<AttachmentSummaryResponse> upload(
            UUID reportId,
            List<MultipartFile> files
    ) {

        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new AppException(ErrorCode.REPORT_NOT_FOUND));

        String folder = StorageFolders.reportFolder(reportId.toString());

        List<Attachment> attachments = new ArrayList<>();

        for (MultipartFile file : files) {

            if (file.isEmpty()) continue;

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

            attachments.add(attachment);
        }

        List<Attachment> saved =
                attachmentRepository.saveAll(attachments);

        List<String> uploadedUrls = saved.stream()
                .map(Attachment::getFileUrl)
                .toList();

        applicationEventPublisher.publishEvent(
                new ReportAttachmentsAddedEvent(
                        reportId,
                        uploadedUrls
                )
        );

        return saved.stream()
                .map(mapper::toResponse)
                .toList();
    }
}