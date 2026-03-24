package com.smartcity.urban_management.modules.department.repository;

import com.smartcity.urban_management.modules.department.dto.office.DepartmentOfficeResponse;
import com.smartcity.urban_management.modules.department.entity.DepartmentOffice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface DepartmentOfficeRepository extends JpaRepository<DepartmentOffice, UUID> {

    Page<DepartmentOffice> findByDepartmentId(UUID departmentId, Pageable pageable);

    long countByDepartmentId(UUID id);

    @Query("""
                SELECT new com.smartcity.urban_management.modules.department.dto.office.DepartmentOfficeResponse(
                    o.id,
                    o.name,
                    o.address,
                    cast(function('ST_Y', o.location) as double) as latitude,
                    cast(function('ST_X', o.location) as double) as longitude,
                    o.isActive,
                    COUNT(u.id)
                )
                FROM DepartmentOffice o
                LEFT JOIN User u ON u.office.id = o.id AND u.deletedAt IS NULL
                WHERE o.department.id = :departmentId
                GROUP BY o.id, o.name, o.address, o.location, o.isActive
            """)
    Page<DepartmentOfficeResponse> findOfficesWithUserCount(
            @Param("departmentId") UUID departmentId,
            Pageable pageable
    );

}