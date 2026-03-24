package com.smartcity.urban_management.modules.department.controller;

import com.smartcity.urban_management.modules.department.dto.office.DepartmentStatsResponse;
import com.smartcity.urban_management.modules.department.service.DepartmentStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/admin/departments")
@RequiredArgsConstructor
public class DepartmentStatsController {

    private final DepartmentStatsService departmentStatsService;

    @GetMapping("/{departmentId}/stats")
    public DepartmentStatsResponse getDepartmentStats(
            @PathVariable UUID departmentId
    ) {
        return departmentStatsService.getDepartmentStats(departmentId);
    }
}