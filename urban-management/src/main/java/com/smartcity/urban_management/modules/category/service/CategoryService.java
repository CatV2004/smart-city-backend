package com.smartcity.urban_management.modules.category.service;

import com.smartcity.urban_management.modules.category.dto.CategoryCreateRequest;
import com.smartcity.urban_management.modules.category.dto.CategoryFilterRequest;
import com.smartcity.urban_management.modules.category.dto.CategoryResponse;
import com.smartcity.urban_management.modules.category.dto.CategoryUpdateRequest;
import com.smartcity.urban_management.shared.pagination.PageRequestDto;
import com.smartcity.urban_management.shared.pagination.PageResponse;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    CategoryResponse create(CategoryCreateRequest request);

    CategoryResponse update(UUID id, CategoryUpdateRequest request);

    void delete(UUID id);

    CategoryResponse findById(UUID id);

    PageResponse<CategoryResponse> getAll(CategoryFilterRequest filter, PageRequestDto request);

    CategoryResponse findBySlug(String slug);
}
