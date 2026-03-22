package com.smartcity.urban_management.modules.department.dto;

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
public class DepartmentSummaryResponse {

    private UUID id;
    private String name;
    private String code;
    private Boolean isActive;
    private LocalDateTime createdAt;
}
