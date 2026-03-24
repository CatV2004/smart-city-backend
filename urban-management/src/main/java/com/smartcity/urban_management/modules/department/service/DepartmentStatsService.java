package com.smartcity.urban_management.modules.department.service;

import com.smartcity.urban_management.modules.department.dto.office.DepartmentStatsResponse;
import com.smartcity.urban_management.modules.department.dto.office.OfficeUserCount;
import com.smartcity.urban_management.modules.department.repository.DepartmentOfficeRepository;
import com.smartcity.urban_management.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DepartmentStatsService {

    private final DepartmentOfficeRepository officeRepository;
    private final UserRepository userRepository;

    public DepartmentStatsResponse getDepartmentStats(UUID departmentId) {

        long totalOffices = officeRepository.countByDepartmentId(departmentId);
        long totalUsers = userRepository.countByDepartment_IdAndDeletedAtIsNull(departmentId);

        return new DepartmentStatsResponse(
                totalOffices,
                totalUsers
        );
    }
}