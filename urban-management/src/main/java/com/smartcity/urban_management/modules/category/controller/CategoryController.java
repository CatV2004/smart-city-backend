package com.smartcity.urban_management.modules.category.controller;

import com.smartcity.urban_management.modules.category.dto.CategoryCreateRequest;
import com.smartcity.urban_management.modules.category.dto.CategoryFilterRequest;
import com.smartcity.urban_management.modules.category.dto.CategoryResponse;
import com.smartcity.urban_management.modules.category.dto.CategoryUpdateRequest;
import com.smartcity.urban_management.modules.category.service.CategoryService;
import com.smartcity.urban_management.shared.pagination.PageRequestDto;
import com.smartcity.urban_management.shared.pagination.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "Get categories")
    @GetMapping
    public PageResponse<CategoryResponse> getAll(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Boolean active,
            @ModelAttribute PageRequestDto request
    ) {

        CategoryFilterRequest filter = new CategoryFilterRequest();
        filter.setKeyword(keyword);
        filter.setActive(active);

        return categoryService.getAll(filter, request);
    }

    @Operation(summary = "Get detail category by slug")
    @GetMapping("/{slug}")
    public CategoryResponse getCategoryBySlug(
            @PathVariable String slug
    ){
        return categoryService.findBySlug(slug);
    }

    @PostMapping
    public CategoryResponse create(
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
    public CategoryResponse update(
            @PathVariable UUID cateId,
            @RequestBody CategoryUpdateRequest request
    ) {
        return categoryService.update(cateId, request);
    }

}