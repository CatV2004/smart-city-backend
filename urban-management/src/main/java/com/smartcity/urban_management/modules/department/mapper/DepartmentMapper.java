package com.smartcity.urban_management.modules.department.mapper;

import com.smartcity.urban_management.modules.department.dto.CreateDepartmentRequest;
import com.smartcity.urban_management.modules.department.dto.DepartmentDetailResponse;
import com.smartcity.urban_management.modules.department.entity.Department;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {

    public DepartmentDetailResponse toDetail(Department d) {
        if (d == null) return null;

        return DepartmentDetailResponse.builder()
                .id(d.getId())
                .name(d.getName())
                .code(d.getCode())
                .description(d.getDescription())
                .isActive(d.getIsActive())
                .createdAt(d.getCreatedAt())
                .updatedAt(d.getUpdatedAt())
                .build();
    }

    public Department toEntity(CreateDepartmentRequest request) {
        if (request == null) return null;

        return Department.builder()
                .name(request.getName())
                .code(request.getCode())
                .description(request.getDescription())
                .isActive(Boolean.TRUE)
                .build();
    }
}