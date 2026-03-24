package com.smartcity.urban_management.modules.department.dto.office;

import java.util.List;

public record DepartmentStatsResponse(
        long totalOffices,
        long totalUsers
) {
}
