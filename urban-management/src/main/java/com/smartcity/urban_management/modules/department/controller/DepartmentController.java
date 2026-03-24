package com.smartcity.urban_management.modules.department.controller;

import com.smartcity.urban_management.modules.department.dto.department.*;
import com.smartcity.urban_management.modules.department.service.DepartmentService;
import com.smartcity.urban_management.shared.pagination.PageRequestDto;
import com.smartcity.urban_management.shared.pagination.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @Operation(summary = "Get departments")
    @GetMapping
    public PageResponse<DepartmentSummaryResponse> getAll(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Boolean active,
            @ModelAttribute PageRequestDto request
    ) {

        DepartmentFilterRequest filter = new DepartmentFilterRequest();
        filter.setKeyword(keyword);
        filter.setActive(active);

        return departmentService.getAll(filter, request);
    }

    @GetMapping("/code/{code}")
    public DepartmentDetailResponse getByCode(@PathVariable String code) {
        return departmentService.getDepartmentDetail(code);
    }

    @GetMapping("/{id}")
    public DepartmentDetailResponse getById(@PathVariable UUID id) {
        return departmentService.getDepartmentDetail(id);
    }

    @GetMapping("/active")
    public List<DepartmentResponse> getActiveDepartments(
            @RequestParam(required = false) List<String> codes
    ) {
        return departmentService.getAllActiveByCodes(codes);
    }

    @PostMapping
    public CreateDepartmentResponse createDepartment(
            @Valid @RequestBody CreateDepartmentRequest request
    ) {
        return departmentService.createDepartment(request);
    }

    @GetMapping("/{id}/has-categories")
    public ResponseEntity<Boolean> hasCategories(@PathVariable UUID id) {
        boolean hasCategories = departmentService.hasCategories(id);
        return ResponseEntity.ok(hasCategories);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(
            @PathVariable("id") UUID departmentId,
            @RequestParam(name = "force", defaultValue = "false") boolean force
    ) {
        departmentService.deleteDepartment(departmentId, force);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UpdateDepartmentResponse> updateDepartment(
            @PathVariable UUID id,
            @RequestBody UpdateDepartmentRequest request
    ) {
        UpdateDepartmentResponse response = departmentService.updateDepartment(id, request);
        return ResponseEntity.ok(response);
    }
}
