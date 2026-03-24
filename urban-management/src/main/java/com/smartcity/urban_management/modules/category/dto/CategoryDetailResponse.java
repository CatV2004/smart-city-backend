package com.smartcity.urban_management.modules.category.dto;
import com.smartcity.urban_management.modules.department.dto.department.DepartmentResponse;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDetailResponse {
    private UUID id;
    private String name;
    private String slug;
    private String description;
    private String icon;
    private String color;
    private String aiClass;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private DepartmentResponse department;
}