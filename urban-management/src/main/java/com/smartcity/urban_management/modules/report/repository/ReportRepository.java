package com.smartcity.urban_management.modules.report.repository;

import com.smartcity.urban_management.modules.report.dto.ReportDetailResponse;
import com.smartcity.urban_management.modules.report.dto.ReportSummaryResponse;
import com.smartcity.urban_management.modules.report.entity.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReportRepository extends JpaRepository<Report, UUID> {
    @Query("""
                SELECT new com.smartcity.urban_management.modules.report.dto.ReportDetailResponse(
                    r.id,
                    r.title,
                    r.description,
                    r.category,
                    r.status,
                    cast(function('ST_Y', r.location) as double),
                    cast(function('ST_X', r.location) as double),
                    r.address,
                    u.fullName,
                    u.id,
                    r.createdAt
                )
                FROM Report r
                JOIN r.createdBy u
                WHERE r.id = :id AND r.deletedAt IS NULL
            """)
    Optional<ReportDetailResponse> findDetailById(UUID id);

    @Query("""
            SELECT new com.smartcity.urban_management.modules.report.dto.ReportSummaryResponse(
                r.id,
                r.title,
                r.description,
                r.category,
                r.status,
                cast(function('ST_Y', r.location) as double),
                cast(function('ST_X', r.location) as double),
                r.address,
                u.fullName,
                u.id,
                r.createdAt
            )
            FROM Report r
            JOIN r.createdBy u
            WHERE r.id = :id AND r.deletedAt IS NULL
            """)
    Optional<ReportSummaryResponse> findSummaryById(UUID id);

    @Query("""
        SELECT new com.smartcity.urban_management.modules.report.dto.ReportSummaryResponse(
            r.id,
            r.title,
            r.description,
            r.category,
            r.status,
            cast(function('ST_Y', r.location) as double),
            cast(function('ST_X', r.location) as double),
            r.address,
            u.fullName,
            u.id,
            r.createdAt
        )
        FROM Report r
        JOIN r.createdBy u
        WHERE r.deletedAt IS NULL
        """)
    Page<ReportSummaryResponse> findAllResponses(Pageable pageable);

    Optional<Report> findByIdAndDeletedAtIsNull(UUID id);

    boolean existsByIdAndCreatedBy_Id(UUID id, UUID userId);
}