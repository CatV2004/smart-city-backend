package com.smartcity.urban_management.modules.department.service;


import com.smartcity.urban_management.modules.department.dto.office.AddUserToOfficeRequest;
import com.smartcity.urban_management.modules.department.dto.office.CreateOfficeResponse;
import com.smartcity.urban_management.modules.department.dto.office.DepartmentOfficeRequest;
import com.smartcity.urban_management.modules.department.dto.office.DepartmentOfficeResponse;
import com.smartcity.urban_management.shared.pagination.PageRequestDto;
import com.smartcity.urban_management.shared.pagination.PageResponse;

import java.util.UUID;

public interface DepartmentOfficeService {

    CreateOfficeResponse create(DepartmentOfficeRequest request);

    PageResponse<DepartmentOfficeResponse> getByDepartment(UUID departmentId, PageRequestDto request);

    void delete(UUID id);

    public void addUserToOffice(UUID officeId, AddUserToOfficeRequest request);
}