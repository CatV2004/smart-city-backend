package com.smartcity.urban_management.modules.category.mapper;

import com.smartcity.urban_management.modules.category.dto.CategoryResponse;
import com.smartcity.urban_management.modules.category.dto.CategoryUpdateRequest;
import com.smartcity.urban_management.modules.category.entity.Category;
import org.springframework.stereotype.Component;
import static com.smartcity.urban_management.shared.util.UpdateUtils.setIfNotNull;

@Component
public class CategoryMapper {

    public CategoryResponse toResponse(Category category) {

        if (category == null) {
            return null;
        }

        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .slug(category.getSlug())
                .description(category.getDescription())
                .icon(category.getIcon())
                .color(category.getColor())
                .aiClass(category.getAiClass())
                .active(category.isActive())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }

    public void updateFromRequest(Category category, CategoryUpdateRequest request) {

        if (request == null) return;

        setIfNotNull(request.getName(), category::setName);
        setIfNotNull(request.getDescription(), category::setDescription);
        setIfNotNull(request.getIcon(), category::setIcon);
        setIfNotNull(request.getColor(), category::setColor);
        setIfNotNull(request.getAiClass(), category::setAiClass);
        setIfNotNull(request.getActive(), category::setActive);
    }
}
