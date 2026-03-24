package com.smartcity.urban_management.modules.dashboard.citizen.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCountDto {
    private String categoryName;
    private long count;
}
