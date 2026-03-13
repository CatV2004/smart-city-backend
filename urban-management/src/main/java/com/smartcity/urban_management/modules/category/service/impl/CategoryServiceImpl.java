package com.smartcity.urban_management.modules.category.service.impl;

import com.smartcity.urban_management.infrastructure.redis.cache.CategoryCacheService;
import com.smartcity.urban_management.modules.category.dto.CategoryCreateRequest;
import com.smartcity.urban_management.modules.category.dto.CategoryResponse;
import com.smartcity.urban_management.modules.category.dto.CategoryUpdateRequest;
import com.smartcity.urban_management.modules.category.entity.Category;
import com.smartcity.urban_management.modules.category.mapper.CategoryMapper;
import com.smartcity.urban_management.modules.category.repository.CategoryRepository;
import com.smartcity.urban_management.modules.category.service.CategoryService;
import com.smartcity.urban_management.shared.exception.AppException;
import com.smartcity.urban_management.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper mapper;
    private final CategoryCacheService categoryCacheService;

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
        categoryCacheService.evictAll();
        categoryCacheService.evictCategory(category.getId());

        return mapper.toResponse(category);
    }

    @Override
    public CategoryResponse update(UUID id, CategoryUpdateRequest request) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        if (request.getName() != null)
            category.setName(request.getName());

        if (request.getSlug() != null)
            category.setSlug(request.getSlug());

        if (request.getDescription() != null)
            category.setDescription(request.getDescription());

        if (request.getIcon() != null)
            category.setIcon(request.getIcon());

        if (request.getColor() != null)
            category.setColor(request.getColor());

        if (request.getAiClass() != null)
            category.setAiClass(request.getAiClass());

        if (request.getIsActive() != null)
            category.setActive(request.getIsActive());

        categoryRepository.save(category);

        return mapper.toResponse(category);
    }

    @Override
    public void delete(UUID id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryResponse findById(UUID id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        return mapper.toResponse(category);
    }

    @Override
    public List<CategoryResponse> getAll() {

        return categoryCacheService.getAll().orElseGet(() -> {
            List<CategoryResponse> list = categoryRepository.findAll()
                    .stream()
                    .map(mapper::toResponse)
                    .toList();

            System.out.println("lisst: " + list);
            categoryCacheService.cacheAll(list);
            return list;
        });
    }
}