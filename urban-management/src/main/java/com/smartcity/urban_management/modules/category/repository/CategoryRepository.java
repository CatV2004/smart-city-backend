package com.smartcity.urban_management.modules.category.repository;

import com.smartcity.urban_management.modules.category.dto.CategoryDetailResponse;
import com.smartcity.urban_management.modules.category.dto.CategoryProjection;
import com.smartcity.urban_management.modules.category.dto.CategorySummaryResponse;
import com.smartcity.urban_management.modules.category.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

    Optional<Category> findBySlug(String slug);

    @Query("SELECT c FROM Category c WHERE c.aiClass = :aiClass")
    Optional<Category> findByAiClass(@Param("aiClass") String aiClass);

    boolean existsBySlug(String slug);

    boolean existsByName(String name);

    boolean existsBySlugAndIdNot(String slug, UUID id);

    @Query("""
            SELECT c
            FROM Category c
            LEFT JOIN FETCH c.department d
            WHERE (:keyword IS NULL OR 
                LOWER(c.name) LIKE LOWER(CONCAT('%', CAST(:keyword AS text), '%')) OR
                LOWER(c.slug) LIKE LOWER(CONCAT('%', CAST(:keyword AS text), '%')))
              AND (:active IS NULL OR c.isActive = :active)
              AND (:departmentId IS NULL OR c.department.id = :departmentId)
            """)
    Page<CategoryProjection> searchProjection(
            String keyword,
            Boolean active,
            UUID departmentId,
            Pageable pageable
    );

    @Query("""
                SELECT c
                FROM Category c
                WHERE c.slug = :slug
            """)
    Optional<CategoryProjection> findDetailProjectionBySlug(String slug);

    boolean existsByDepartmentId(UUID departmentId);

    @Modifying
    @Query("DELETE FROM Category c WHERE c.department.id = :departmentId")
    void deleteByDepartmentId(UUID departmentId);

    @Query("SELECT c.slug FROM Category c WHERE c.department.id = :departmentId")
    List<String> findSlugsByDepartmentId(@Param("departmentId") UUID departmentId);

    List<Category> findAllByIsActiveTrue();}
