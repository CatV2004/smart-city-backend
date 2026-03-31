package com.smartcity.urban_management.modules.task.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EvidenceDto {
    private String fileUrl;
    private String fileName;
    private LocalDateTime createdAt;
}
