package com.smartcity.urban_management.modules.department.repository;

import com.smartcity.urban_management.modules.department.dto.department.DepartmentResponse;
import com.smartcity.urban_management.modules.department.dto.department.DepartmentSummaryResponse;
import com.smartcity.urban_management.modules.department.entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DepartmentRepository extends JpaRepository<Department, UUID> {

    @Query("""
                SELECT new com.smartcity.urban_management.modules.department.dto.department.DepartmentSummaryResponse(
                    d.id,
                    d.name,
                    d.code,
                    d.isActive,
                    d.createdAt
                )
                FROM Department d
                WHERE d.deletedAt IS NULL
                AND (:keyword IS NULL OR LOWER(d.name) LIKE LOWER(CONCAT('%', CAST(:keyword AS text), '%')))
                AND (:active IS NULL OR d.isActive = :active)
            """)
    Page<DepartmentSummaryResponse> search(
            @Param("keyword") String keyword,
            @Param("active") Boolean active,
            Pageable pageable
    );

    Optional<Department> findByCode(String code);

    boolean existsByCode(String code);

    List<Department> findByIsActiveTrue();

    List<Department> findByIsActiveTrueAndCodeIn(List<String> codes);

    @Query("""
                SELECT new com.smartcity.urban_management.modules.department.dto.department.DepartmentResponse(
                    c.department.id,
                    c.department.name,
                    c.department.code
                )
                FROM Category c
                WHERE c.id = :categoryId
            """)
    DepartmentResponse findDepartmentByCategoryId(@Param("categoryId") UUID categoryId);

    boolean existsByNameAndIdNot(String name, UUID id);
}
