package com.smartcity.urban_management.modules.category.dto;

import com.smartcity.urban_management.modules.department.dto.DepartmentResponse;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategorySummaryResponse {
    private UUID id;
    private String name;
    private String slug;
    private String description;
    private String icon;
    private String color;
    private boolean active;
    private LocalDateTime createdAt;
    private DepartmentResponse department;
}