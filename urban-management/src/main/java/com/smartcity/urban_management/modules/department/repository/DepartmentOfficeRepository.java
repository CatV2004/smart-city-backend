package com.smartcity.urban_management.modules.department.repository;

import com.smartcity.urban_management.modules.department.dto.office.DepartmentOfficeResponse;
import com.smartcity.urban_management.modules.department.entity.DepartmentOffice;
import com.smartcity.urban_management.modules.location.dto.projection.OfficeMapProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
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

    @Query(value = """
                SELECT
                    o.id,
                    o.name,
                    o.address,
                    d.name as departmentName,
                    ST_Y(o.location) as lat,
                    ST_X(o.location) as lng
            
                FROM department_offices o
                JOIN departments d ON o.department_id = d.id
            
                WHERE o.is_active = true
            
                AND (
                    o.department_id IN (:departmentIds)
                )
            
                AND (
                    :keyword IS NULL OR
                    LOWER(o.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
                    LOWER(o.address) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
                    LOWER(d.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
                )
            """, nativeQuery = true)
    List<OfficeMapProjection> findAllForMapFiltered(
            @Param("departmentIds") List<UUID> departmentIds,
            @Param("keyword") String keyword
    );

    @Query("""
                SELECT o.id as id,
                       o.name as name,
                       o.address as address,
                       ST_Y(o.location) as lat,
                        ST_X(o.location) as lng,
                       d.name as departmentName
                FROM User u
                JOIN u.office o
                JOIN o.department d
                WHERE u.id = :userId
            """)
    Optional<OfficeMapProjection> findOfficeByUserIdForMap(UUID userId);

    @Query(value = """
            SELECT o.*
            FROM department_offices o
            WHERE o.department_id = :departmentId
              AND o.is_active = true
            ORDER BY ST_DistanceSphere(
                o.location,
                ST_SetSRID(ST_MakePoint(:lng, :lat), 4326)
            )
            LIMIT :limit
            """, nativeQuery = true)
    List<DepartmentOffice> findNearestOffices(
            @Param("departmentId") UUID departmentId,
            @Param("lat") double lat,
            @Param("lng") double lng,
            @Param("limit") int limit
    );

}