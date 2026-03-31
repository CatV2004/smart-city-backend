package com.smartcity.urban_management.modules.report.dto;

import com.smartcity.urban_management.modules.task.dto.EvidenceDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportResultDto {
    private LocalDateTime completedAt;
    private String note;
    private List<EvidenceDto> evidences;
}
