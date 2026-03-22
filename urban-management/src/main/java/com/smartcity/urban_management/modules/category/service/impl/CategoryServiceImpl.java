package com.smartcity.urban_management.modules.category.service.impl;

import com.smartcity.urban_management.infrastructure.redis.cache.CategoryCacheService;
import com.smartcity.urban_management.modules.category.dto.*;
import com.smartcity.urban_management.modules.category.entity.Category;
import com.smartcity.urban_management.modules.category.mapper.CategoryMapper;
import com.smartcity.urban_management.modules.category.pagination.CategorySortField;
import com.smartcity.urban_management.modules.category.repository.CategoryRepository;
import com.smartcity.urban_management.modules.category.service.CategoryService;
import com.smartcity.urban_management.modules.department.dto.DepartmentResponse;
import com.smartcity.urban_management.modules.department.entity.Department;
import com.smartcity.urban_management.modules.department.repository.DepartmentRepository;
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
    private final DepartmentRepository departmentRepository;
    private final CategoryCacheService categoryCacheService;
    private final CategorySortField categorySortField = new CategorySortField();

    @Transactional
    @Override
    public CategoryDetailResponse create(CategoryCreateRequest request) {
        if (request == null) {
            throw new AppException(ErrorCode.BAD_REQUEST);
        }

        // ===== NORMALIZE SLUG =====
        String slug = request.getSlug().trim().toLowerCase();

        // ===== CHECK DUPLICATE =====
        if (categoryRepository.existsBySlug(slug)) {
            throw new AppException(ErrorCode.CATEGORY_SLUG_DUPLICATE);
        }

        // ===== LOAD DEPARTMENT =====
        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND));

        // ===== CREATE ENTITY =====
        Category category = Category.builder()
                .name(request.getName())
                .slug(slug)
                .description(request.getDescription())
                .icon(request.getIcon())
                .color(request.getColor())
                .aiClass(request.getAiClass())
                .department(department)
                .isActive(true)
                .build();
        categoryRepository.save(category);

        // ===== CLEAR CACHE =====
        categoryCacheService.evictAllPages();

        // ===== QUERY PROJECTION DETAIL =====
        CategoryProjection projection = categoryRepository.findDetailProjectionBySlug(slug)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        // ===== MAP TO DTO =====
        CategoryDetailResponse response = CategoryMapper.mapToDetail(projection);

        // ===== CACHE DETAIL =====
        categoryCacheService.cacheCategoryBySlug(slug, response);

        return response;
    }

    @Transactional
    @Override
    public CategoryDetailResponse update(UUID id, CategoryUpdateRequest request) {
        if (request == null) {
            throw new AppException(ErrorCode.BAD_REQUEST);
        }

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        String oldSlug = category.getSlug();

        // ===== HANDLE SLUG =====
        if (request.getSlug() != null) {
            String newSlug = request.getSlug().trim().toLowerCase();
            if (!newSlug.equals(oldSlug)) {
                boolean exists = categoryRepository.existsBySlugAndIdNot(newSlug, id);
                if (exists) {
                    throw new AppException(ErrorCode.CATEGORY_SLUG_DUPLICATE);
                }
                category.setSlug(newSlug);
            }
        }

        // ===== HANDLE DEPARTMENT =====
        if (request.getDepartmentId() != null) {
            Department department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND));
            category.setDepartment(department);
        }

        // ===== UPDATE FIELDS =====
        CategoryMapper.updateFromRequest(category, request);

        // ===== CACHE INVALIDATION =====
        categoryCacheService.evictCategoryBySlug(oldSlug);
        categoryCacheService.evictAllPages();

        // ===== QUERY PROJECTION DETAIL =====
        CategoryProjection projection = categoryRepository.findDetailProjectionBySlug(category.getSlug())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        return CategoryMapper.mapToDetail(projection);
    }

    @Override
    public void delete(UUID id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        categoryRepository.deleteById(id);

        categoryCacheService.evictCategoryBySlug(category.getSlug());
        categoryCacheService.evictAllPages();
    }

    @Override
    public CategoryDetailResponse findBySlug(String slug) {
        return categoryCacheService.getCategoryBySlug(slug)
                .orElseGet(() -> {
                    // ===== QUERY DTO PROJECTION =====
                    CategoryProjection projection = categoryRepository.findDetailProjectionBySlug(slug)
                            .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

                    // ===== MAP PROJECTION TO DETAIL DTO =====
                    CategoryDetailResponse response = CategoryMapper.mapToDetail(projection);

                    // ===== LOAD DEPARTMENT SEPARATELY =====
                    DepartmentResponse dept = getDepartment(projection.getId());
                    response.setDepartment(dept);

                    // ===== CACHE DTO =====
                    categoryCacheService.cacheCategoryBySlug(slug, response);

                    return response;
                });
    }

    @Override
    public PageResponse<CategorySummaryResponse> getAll(CategoryFilterRequest filter, PageRequestDto request) {

        int page = request.getPage();
        int size = request.getSize();
        String sort = request.getSort();

        String filterKey = buildFilterKey(filter);

        // ===== CACHE CHECK =====
        Optional<PageResponse<CategorySummaryResponse>> cached =
                categoryCacheService.getCategoryPage(page, size, sort, filterKey);

        if (cached.isPresent()) {
            return cached.get();
        }

        // ===== CREATE PAGEABLE =====
        Pageable pageable = PageableFactory.from(request, categorySortField);

        // ===== PROCESS KEYWORD =====
        String keyword = filter.getKeyword();
        if (keyword != null) {
            keyword = keyword.trim();
            if (keyword.isEmpty()) {
                keyword = null;
            }
        }

        Boolean active = filter.getActive();
        UUID departmentId = filter.getDepartmentId();

        // ===== QUERY PROJECTION =====
        Page<CategoryProjection> pageData =
                categoryRepository.searchProjection(keyword, active, departmentId, pageable);

        // ===== MAP PROJECTION TO DTO =====
        Page<CategorySummaryResponse> content = pageData.map(proj -> {
            CategorySummaryResponse dto = CategoryMapper.mapToSummary(proj);

            DepartmentResponse dept = getDepartment(proj.getId());
            dto.setDepartment(dept);

            return dto;
        });

        PageResponse<CategorySummaryResponse> response = PageMapper.toResponse(content);

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

    @Override
    public List<CategorySummaryResponse> getAllActive() {

        List<Category> categories = categoryRepository.findAllByIsActiveTrue();

        return categories.stream()
                .map(category -> CategorySummaryResponse.builder()
                        .id(category.getId())
                        .name(category.getName())
                        .build()
                )
                .toList();
    }

    private DepartmentResponse getDepartment(UUID cateId) {
        return departmentRepository.findDepartmentByCategoryId(cateId);
    }

    private String buildFilterKey(CategoryFilterRequest filter) {
        return String.format(
                "keyword:%s|active:%s|departmentId:%s",
                filter.getKeyword() == null ? "" : filter.getKeyword(),
                filter.getActive() == null ? "" : filter.getActive(),
                filter.getDepartmentId() == null ? "" : filter.getDepartmentId()
        );
    }
}