package com.smartcity.urban_management.modules.category.service;

import com.smartcity.urban_management.modules.category.dto.*;
import com.smartcity.urban_management.shared.pagination.PageRequestDto;
import com.smartcity.urban_management.shared.pagination.PageResponse;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    CategoryDetailResponse create(CategoryCreateRequest request);

    CategoryDetailResponse update(UUID id, CategoryUpdateRequest request);

    void delete(UUID id);

    PageResponse<CategorySummaryResponse> getAll(CategoryFilterRequest filter, PageRequestDto request);

    List<CategorySummaryResponse> getAllActive();

    CategoryDetailResponse findBySlug(String slug);
}
