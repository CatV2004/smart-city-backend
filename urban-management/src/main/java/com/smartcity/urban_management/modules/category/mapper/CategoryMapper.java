package com.smartcity.urban_management.modules.category.mapper;

import com.smartcity.urban_management.modules.category.dto.CategoryDetailResponse;
import com.smartcity.urban_management.modules.category.dto.CategoryProjection;
import com.smartcity.urban_management.modules.category.dto.CategorySummaryResponse;
import com.smartcity.urban_management.modules.category.dto.CategoryUpdateRequest;
import com.smartcity.urban_management.modules.category.entity.Category;
import lombok.experimental.UtilityClass;

import static com.smartcity.urban_management.shared.util.UpdateUtils.setIfNotNull;

@UtilityClass
public class CategoryMapper {

    public void updateFromRequest(Category category, CategoryUpdateRequest request) {

        if (request == null) return;

        setIfNotNull(request.getName(), category::setName);
        setIfNotNull(request.getDescription(), category::setDescription);
        setIfNotNull(request.getIcon(), category::setIcon);
        setIfNotNull(request.getColor(), category::setColor);
        setIfNotNull(request.getAiClass(), category::setAiClass);
        setIfNotNull(request.getActive(), category::setActive);
    }

    public CategorySummaryResponse mapToSummary(CategoryProjection p) {
        return CategorySummaryResponse.builder()
                .id(p.getId())
                .name(p.getName())
                .slug(p.getSlug())
                .description(p.getDescription())
                .icon(p.getIcon())
                .color(p.getColor())
                .active(p.getActive())
                .createdAt(p.getCreatedAt())
                .build();
    }

    public CategoryDetailResponse mapToDetail(CategoryProjection p) {
        return CategoryDetailResponse.builder()
                .id(p.getId())
                .name(p.getName())
                .slug(p.getSlug())
                .description(p.getDescription())
                .icon(p.getIcon())
                .color(p.getColor())
                .aiClass(p.getAiClass())
                .active(p.getActive())
                .createdAt(p.getCreatedAt())
                .updatedAt(p.getUpdatedAt())
                .build();
    }
}
