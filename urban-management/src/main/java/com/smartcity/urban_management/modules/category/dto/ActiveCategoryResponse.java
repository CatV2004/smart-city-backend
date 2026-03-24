package com.smartcity.urban_management.modules.category.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActiveCategoryResponse {
    private List<CategorySummaryResponse> activeCategories;
}
