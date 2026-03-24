package com.smartcity.urban_management.modules.department.mapper;

import com.smartcity.urban_management.modules.department.dto.office.DepartmentOfficeResponse;
import com.smartcity.urban_management.modules.department.entity.DepartmentOffice;
import org.locationtech.jts.geom.Point;

public class DepartmentOfficeMapper {

    public static DepartmentOfficeResponse toResponse(DepartmentOffice entity) {
        return DepartmentOfficeResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .address(entity.getAddress())
                .latitude(entity.getLocation().getY())
                .longitude(entity.getLocation().getX())
                .isActive(entity.getIsActive())
                .build();
    }
}