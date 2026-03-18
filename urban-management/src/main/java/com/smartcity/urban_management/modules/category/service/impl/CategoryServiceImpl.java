package com.smartcity.urban_management.modules.category.service.impl;

import com.smartcity.urban_management.infrastructure.redis.cache.CategoryCacheService;
import com.smartcity.urban_management.modules.category.dto.CategoryCreateRequest;
import com.smartcity.urban_management.modules.category.dto.CategoryFilterRequest;
import com.smartcity.urban_management.modules.category.dto.CategoryResponse;
import com.smartcity.urban_management.modules.category.dto.CategoryUpdateRequest;
import com.smartcity.urban_management.modules.category.entity.Category;
import com.smartcity.urban_management.modules.category.mapper.CategoryMapper;
import com.smartcity.urban_management.modules.category.pagination.CategorySortField;
import com.smartcity.urban_management.modules.category.repository.CategoryRepository;
import com.smartcity.urban_management.modules.category.service.CategoryService;
import com.smartcity.urban_management.shared.exception.AppException;
import com.smartcity.urban_management.shared.exception.ErrorCode;
import com.smartcity.urban_management.shared.pagination.PageMapper;
import com.smartcity.urban_management.shared.pagination.PageRequestDto;
import com.smartcity.urban_management.shared.pagination.PageResponse;
import com.smartcity.urban_management.shared.pagination.PageableFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.smartcity.urban_management.shared.util.UpdateUtils.setIfNotNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper mapper;
    private final CategoryCacheService categoryCacheService;
    private final CategorySortField categorySortField = new CategorySortField();

    @Override
    public CategoryResponse create(CategoryCreateRequest request) {

        if (categoryRepository.existsBySlug(request.getSlug())) {
            throw new AppException(ErrorCode.CATEGORY_SLUG_DUPLICATE);
        }

        Category category = Category.builder()
                .name(request.getName())
                .slug(request.getSlug())
                .description(request.getDescription())
                .icon(request.getIcon())
                .color(request.getColor())
                .aiClass(request.getAiClass())
                .isActive(true)
                .build();

        categoryRepository.save(category);
        categoryCacheService.evictAllCategories();
        categoryCacheService.evictAllPages();

        return mapper.toResponse(category);
    }

    @Transactional
    @Override
    public CategoryResponse update(UUID id, CategoryUpdateRequest request) {

        if (request == null) {
            throw new AppException(ErrorCode.BAD_REQUEST);
        }

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        if (request.getSlug() != null && !request.getSlug().equals(category.getSlug())) {
            if (categoryRepository.existsBySlug(request.getSlug())) {
                throw new AppException(ErrorCode.CATEGORY_SLUG_DUPLICATE);
            }
            category.setSlug(request.getSlug());
        }

        mapper.updateFromRequest(category, request);

        categoryRepository.save(category);

        categoryCacheService.evictCategory(id);
        categoryCacheService.evictAllCategories();
        categoryCacheService.evictAllPages();

        return mapper.toResponse(category);
    }

    @Override
    public void delete(UUID id) {
        categoryRepository.deleteById(id);

        categoryCacheService.evictCategory(id);
        categoryCacheService.evictAllCategories();
        categoryCacheService.evictAllPages();
    }

    @Override
    public CategoryResponse findById(UUID id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        return mapper.toResponse(category);
    }

    @Override
    public CategoryResponse findBySlug(String slug) {
        Category category = categoryRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        return mapper.toResponse(category);
    }

    @Override
    public PageResponse<CategoryResponse> getAll(CategoryFilterRequest filter, PageRequestDto request) {

        int page = request.getPage();
        int size = request.getSize();
        String sort = request.getSort();

        String filterKey = buildFilterKey(filter);

        // ===== CACHE CHECK =====
        Optional<PageResponse<CategoryResponse>> cached =
                categoryCacheService.getCategoryPage(page, size, sort, filterKey);

        if (cached.isPresent()) {
            return cached.get();
        }

        // ===== CREATE PAGEABLE =====
        Pageable pageable = PageableFactory.from(request, categorySortField);

        // ===== KEYWORD =====
        String keyword = filter.getKeyword();
        if (keyword != null && keyword.isBlank()) {
            keyword = null;
        }

        Boolean active = filter.getActive();

        // ===== QUERY =====
        Page<Category> pageResult =
                categoryRepository.search(keyword, active, pageable);

        // ===== MAP =====
        Page<CategoryResponse> mapped =
                pageResult.map(mapper::toResponse);

        PageResponse<CategoryResponse> response =
                PageMapper.toResponse(mapped);

        // ===== CACHE =====
        categoryCacheService.cacheCategoryPage(
                page,
                size,
                sort,
                filterKey,
                response
        );

        return response;
    }

    private String buildFilterKey(CategoryFilterRequest filter) {

        return String.format(
                "keyword:%s|active:%s",
                filter.getKeyword(),
                filter.getActive()
        );
    }
}