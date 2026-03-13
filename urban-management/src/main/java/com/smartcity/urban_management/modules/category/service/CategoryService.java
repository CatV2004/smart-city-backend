package com.smartcity.urban_management.modules.category.service;

import com.smartcity.urban_management.modules.category.dto.CategoryCreateRequest;
import com.smartcity.urban_management.modules.category.dto.CategoryResponse;
import com.smartcity.urban_management.modules.category.dto.CategoryUpdateRequest;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    CategoryResponse create(CategoryCreateRequest request);

    CategoryResponse update(UUID id, CategoryUpdateRequest request);

    void delete(UUID id);

    CategoryResponse findById(UUID id);

    List<CategoryResponse> getAll();
}
