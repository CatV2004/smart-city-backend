package com.smartcity.urban_management.modules.category.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CategoryCreateRequest {

    private String name;

    private String slug;

    private String description;

    private String icon;

    private String color;

    private String aiClass;

    private UUID departmentId;
}