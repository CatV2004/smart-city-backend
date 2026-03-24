package com.smartcity.urban_management.modules.department.dto.department;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDetailResponse {

    private UUID id;
    private String name;
    private String code;
    private String description;
    private Boolean isActive;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
