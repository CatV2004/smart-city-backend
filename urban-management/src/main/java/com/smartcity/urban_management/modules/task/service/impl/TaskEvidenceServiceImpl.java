package com.smartcity.urban_management.modules.task.service.impl;

import com.smartcity.urban_management.infrastructure.storage.FileStorageService;
import com.smartcity.urban_management.infrastructure.storage.FileUploadResult;
import com.smartcity.urban_management.infrastructure.storage.StorageFolders;
import com.smartcity.urban_management.modules.task.entity.Task;
import com.smartcity.urban_management.modules.task.entity.TaskEvidence;
import com.smartcity.urban_management.modules.task.repository.TaskEvidenceRepository;
import com.smartcity.urban_management.modules.task.service.TaskEvidenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TaskEvidenceServiceImpl implements TaskEvidenceService {
    private final FileStorageService fileStorageService;
    private final TaskEvidenceRepository taskEvidenceRepository;

    @Override
    public void uploadEvidences(Task task, List<MultipartFile> files) {

        if (files == null || files.isEmpty()) return;

        String folder = StorageFolders.reportFolder(
                task.getReport().getId().toString()
        );

        for (MultipartFile file : files) {

            FileUploadResult uploadResult =
                    fileStorageService.upload(file, folder);

            TaskEvidence evidence = TaskEvidence.builder()
                    .task(task)
                    .fileUrl(uploadResult.getFileUrl())
                    .fileName(file.getOriginalFilename())
                    .build();

            taskEvidenceRepository.save(evidence);
        }
    }
}
