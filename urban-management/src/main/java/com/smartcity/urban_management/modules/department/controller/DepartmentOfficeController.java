package com.smartcity.urban_management.modules.department.controller;

import com.smartcity.urban_management.modules.department.dto.office.AddUserToOfficeRequest;
import com.smartcity.urban_management.modules.department.dto.office.CreateOfficeResponse;
import com.smartcity.urban_management.modules.department.dto.office.DepartmentOfficeRequest;
import com.smartcity.urban_management.modules.department.dto.office.DepartmentOfficeResponse;
import com.smartcity.urban_management.modules.department.service.DepartmentOfficeService;
import com.smartcity.urban_management.shared.pagination.PageRequestDto;
import com.smartcity.urban_management.shared.pagination.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/department-offices")
@RequiredArgsConstructor
public class DepartmentOfficeController {

    private final DepartmentOfficeService service;

    @PostMapping
    public CreateOfficeResponse create(@RequestBody DepartmentOfficeRequest request) {
        return service.create(request);
    }

    @Operation(summary = "Get offices by department with pagination")
    @GetMapping("/department/{departmentId}")
    public PageResponse<DepartmentOfficeResponse> getByDepartment(
            @PathVariable UUID departmentId,
            @ModelAttribute PageRequestDto request
    ) {
        return service.getByDepartment(departmentId, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @PostMapping("/{officeId}/users")
    public void addUserToOffice(
            @PathVariable UUID officeId,
            @RequestBody AddUserToOfficeRequest request
    ) {
        service.addUserToOffice(officeId, request);
    }
}