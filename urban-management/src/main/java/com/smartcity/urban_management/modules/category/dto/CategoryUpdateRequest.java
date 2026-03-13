package com.smartcity.urban_management.modules.category.dto;

import lombok.Data;

@Data
public class CategoryUpdateRequest {

    private String name;

    private String slug;

    private String description;

    private String icon;

    private String color;

    private String aiClass;

    private Boolean isActive;
}