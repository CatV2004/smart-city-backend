package com.smartcity.urban_management.modules.category.service.impl;

import com.smartcity.urban_management.infrastructure.redis.cache.CategoryCacheService;
import com.smartcity.urban_management.modules.category.dto.*;
import com.smartcity.urban_management.modules.category.entity.Category;
import com.smartcity.urban_management.modules.category.mapper.CategoryMapper;
import com.smartcity.urban_management.modules.category.pagination.CategorySortField;
import com.smartcity.urban_management.modules.category.repository.CategoryRepository;
import com.smartcity.urban_management.modules.category.service.CategoryService;
import com.smartcity.urban_management.modules.department.dto.department.DepartmentResponse;
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

import java.util.List;
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

        // ===== QUERY PROJECTION DETAIL =====
        CategoryProjection projection = categoryRepository.findDetailProjectionBySlug(slug)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        // ===== MAP TO DTO =====
        return CategoryMapper.mapToDetail(projection);
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
    }

    @Override
    public CategoryDetailResponse findBySlug(String slug) {

        // ===== QUERY DTO PROJECTION =====
        CategoryProjection projection = categoryRepository.findDetailProjectionBySlug(slug)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        // ===== MAP PROJECTION TO DETAIL DTO =====
        CategoryDetailResponse response = CategoryMapper.mapToDetail(projection);

        // ===== LOAD DEPARTMENT =====
        DepartmentResponse dept = getDepartment(projection.getId());
        response.setDepartment(dept);

        return response;
    }

    @Override
    public PageResponse<CategorySummaryResponse> getAll(
            CategoryFilterRequest filter,
            PageRequestDto request
    ) {

        int page = request.getPage();
        int size = request.getSize();

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

        // ===== MAP =====
        Page<CategorySummaryResponse> content = pageData.map(proj -> {
            CategorySummaryResponse dto = CategoryMapper.mapToSummary(proj);

            DepartmentResponse dept = getDepartment(proj.getId());
            dto.setDepartment(dept);

            return dto;
        });

        return PageMapper.toResponse(content);
    }

    @Override
    public ActiveCategoryResponse getAllActive() {
        List<Category> categories = categoryRepository.findAllByIsActiveTrue();

        List<CategorySummaryResponse> categoryDtos = categories.stream()
                .map(category -> CategorySummaryResponse.builder()
                        .id(category.getId())
                        .name(category.getName())
                        .active(category.isActive())
                        .build()
                )
                .toList();

        return ActiveCategoryResponse.builder()
                .activeCategories(categoryDtos)
                .build();
    }

    private DepartmentResponse getDepartment(UUID cateId) {
        return departmentRepository.findDepartmentByCategoryId(cateId);
    }

    public String getSlugById(UUID id) {
        return categoryRepository.findById(id)
                .map(Category::getSlug)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
    }
}