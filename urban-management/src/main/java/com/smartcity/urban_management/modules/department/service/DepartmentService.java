package com.smartcity.urban_management.modules.department.service;

import com.smartcity.urban_management.modules.department.dto.department.*;
import com.smartcity.urban_management.shared.pagination.PageRequestDto;
import com.smartcity.urban_management.shared.pagination.PageResponse;

import java.util.List;
import java.util.UUID;

public interface DepartmentService {
    PageResponse<DepartmentSummaryResponse> getAll(
            DepartmentFilterRequest filter,
            PageRequestDto request
    );

    DepartmentDetailResponse getDepartmentDetail(String code);

    DepartmentDetailResponse getDepartmentDetail(UUID id);

    CreateDepartmentResponse createDepartment(CreateDepartmentRequest request);

    List<DepartmentResponse> getAllActiveByCodes(List<String> codes);

    void deleteDepartment(UUID id, boolean force);

    boolean hasCategories(UUID departmentId);

    UpdateDepartmentResponse updateDepartment(UUID id, UpdateDepartmentRequest request);
}
