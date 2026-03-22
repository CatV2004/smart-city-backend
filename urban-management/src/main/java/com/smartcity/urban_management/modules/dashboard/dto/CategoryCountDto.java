package com.smartcity.urban_management.modules.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryCountDto {
    private String categoryName;
    private long count;
}
