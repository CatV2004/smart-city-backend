package com.smartcity.urban_management.modules.category.controller;

import com.smartcity.urban_management.modules.category.dto.*;
import com.smartcity.urban_management.modules.category.service.CategoryService;
import com.smartcity.urban_management.shared.pagination.PageRequestDto;
import com.smartcity.urban_management.shared.pagination.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "Get categories")
    @GetMapping
    public PageResponse<CategorySummaryResponse> getAll(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) UUID departmentId,
            @ModelAttribute PageRequestDto request
    ) {

        CategoryFilterRequest filter = new CategoryFilterRequest();
        filter.setKeyword(keyword);
        filter.setActive(active);
        filter.setDepartmentId(departmentId);

        return categoryService.getAll(filter, request);
    }

    @GetMapping("/active")
    public ActiveCategoryResponse getAllActive() {
        return categoryService.getAllActive();
    }

    @Operation(summary = "Get detail category by slug")
    @GetMapping("/{slug}")
    public CategoryDetailResponse getCategoryBySlug(
            @PathVariable String slug
    ) {
        return categoryService.findBySlug(slug);
    }

    @PostMapping
    public CategoryDetailResponse create(
            @RequestBody CategoryCreateRequest request
    ) {
        return categoryService.create(request);
    }

    @DeleteMapping("/{cateId}")
    public void delete(
            @PathVariable UUID cateId
    ) {
        categoryService.delete(cateId);
    }

    @PatchMapping("/{cateId}")
    @Operation(summary = "Update category")
    public CategoryDetailResponse update(
            @PathVariable UUID cateId,
            @RequestBody CategoryUpdateRequest request
    ) {
        return categoryService.update(cateId, request);
    }

}