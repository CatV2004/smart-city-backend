package com.smartcity.urban_management.modules.task.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class CompleteTaskRequest {

    private String note;

    private List<MultipartFile> files; // optional
}