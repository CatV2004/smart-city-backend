package com.smartcity.urban_management.modules.category.service.impl;

import com.smartcity.urban_management.infrastructure.redis.cache.CategoryCacheService;
import com.smartcity.urban_management.modules.category.dto.*;
import com.smartcity.urban_management.modules.category.service.CategoryService;
import com.smartcity.urban_management.shared.pagination.PageRequestDto;
import com.smartcity.urban_management.shared.pagination.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Primary
public class CategoryCachedService implements CategoryService {

    private final CategoryServiceImpl delegate;
    private final CategoryCacheService cache;

    @Override
    public CategoryDetailResponse create(CategoryCreateRequest request) {
        // ===== CALL CORE BUSINESS =====
        CategoryDetailResponse response = delegate.create(request);

        // ===== CACHE INVALIDATION =====
        cache.evictAllPages();

        // ===== CACHE WRITE =====
        cache.cacheCategoryBySlug(response.getSlug(), response);

        cache.evictAllActiveCategories();

        return response;
    }

    @Override
    public CategoryDetailResponse update(UUID id, CategoryUpdateRequest request) {

        String oldSlug = delegate.getSlugById(id);

        // ===== CALL BUSINESS =====
        CategoryDetailResponse response = delegate.update(id, request);

        String newSlug = response.getSlug();

        // ===== INVALIDATE =====
        cache.evictCategoryBySlug(oldSlug);

        if (!oldSlug.equals(newSlug)) {
            cache.evictCategoryBySlug(newSlug);
        }

        cache.evictAllPages();

        // ===== CACHE WRITE =====
        cache.cacheCategoryBySlug(newSlug, response);

        cache.evictAllActiveCategories();

        return response;
    }

    @Override
    public void delete(UUID id) {

        // ===== GET OLD SLUG FROM DB =====
        String slug = delegate.getSlugById(id);

        // ===== DELETE BUSINESS =====
        delegate.delete(id);

        // ===== INVALIDATE CACHE =====
        cache.evictCategoryBySlug(slug);
        cache.evictAllPages();

        cache.evictAllActiveCategories();
    }

    @Override
    public CategoryDetailResponse findBySlug(String slug) {

        return cache.getCategoryBySlug(slug)
                .orElseGet(() -> {

                    // ===== CALL DB =====
                    CategoryDetailResponse response = delegate.findBySlug(slug);

                    // ===== CACHE =====
                    cache.cacheCategoryBySlug(slug, response);

                    return response;
                });
    }

    @Override
    public PageResponse<CategorySummaryResponse> getAll(
            CategoryFilterRequest filter,
            PageRequestDto request
    ) {

        int page = request.getPage();
        int size = request.getSize();
        String sort = request.getSort();

        String filterKey = buildFilterKey(filter);

        // ===== CACHE CHECK =====
        return cache.getCategoryPage(page, size, sort, filterKey)
                .orElseGet(() -> {

                    // ===== CALL DB =====
                    PageResponse<CategorySummaryResponse> response =
                            delegate.getAll(filter, request);

                    // ===== CACHE =====
                    cache.cacheCategoryPage(page, size, sort, filterKey, response);

                    return response;
                });
    }

    @Override
    public ActiveCategoryResponse getAllActive() {
        return cache.getAllActiveCategories()
                .orElseGet(() -> {
                    ActiveCategoryResponse response = delegate.getAllActive();

                    cache.cacheAllActiveCategories(response);

                    return response;
                });
    }

    private String buildFilterKey(CategoryFilterRequest filter) {
        return String.format(
                "keyword=%s|active=%s|dept=%s",
                normalize(filter.getKeyword()),
                filter.getActive(),
                filter.getDepartmentId()
        );
    }

    private String normalize(String s) {
        return (s == null || s.trim().isEmpty()) ? "null" : s.trim().toLowerCase();
    }
}
