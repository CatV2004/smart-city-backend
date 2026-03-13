package com.smartcity.urban_management.modules.category.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class CategoryResponse {

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
}