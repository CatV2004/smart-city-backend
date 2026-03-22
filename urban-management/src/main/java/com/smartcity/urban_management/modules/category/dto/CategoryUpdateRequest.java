package com.smartcity.urban_management.modules.category.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CategoryUpdateRequest {

    private String name;

    private String slug;

    private String description;

    private UUID departmentId;

    private String icon;

    private String color;

    private String aiClass;

    private Boolean active;
}