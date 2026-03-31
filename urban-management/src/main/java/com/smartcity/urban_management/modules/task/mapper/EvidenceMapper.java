package com.smartcity.urban_management.modules.task.mapper;

import com.smartcity.urban_management.modules.task.dto.EvidenceDto;
import com.smartcity.urban_management.modules.task.entity.TaskEvidence;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class EvidenceMapper {

    public EvidenceDto toDto(TaskEvidence e) {
        if (e == null) return null;

        return EvidenceDto.builder()
                .fileUrl(e.getFileUrl())
                .fileName(e.getFileName())
                .createdAt(e.getCreatedAt())
                .build();
    }

    public List<EvidenceDto> toDtoList(List<TaskEvidence> evidences) {
        return evidences.stream()
                .map(EvidenceMapper::toDto)
                .toList();
    }
}
