package com.smartcity.urban_management.modules.category.controller;

import com.smartcity.urban_management.modules.category.dto.CategoryCreateRequest;
import com.smartcity.urban_management.modules.category.dto.CategoryResponse;
import com.smartcity.urban_management.modules.category.service.CategoryService;
import com.smartcity.urban_management.security.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryResponse> getAll() {
        return categoryService.getAll();
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

}