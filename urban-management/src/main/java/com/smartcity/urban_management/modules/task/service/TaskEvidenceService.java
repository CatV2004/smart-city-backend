package com.smartcity.urban_management.modules.task.service;

import com.smartcity.urban_management.modules.task.entity.Task;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TaskEvidenceService {
    void uploadEvidences(Task task, List<MultipartFile> files);
}
