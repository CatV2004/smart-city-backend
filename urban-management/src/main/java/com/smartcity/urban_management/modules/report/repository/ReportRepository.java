package com.smartcity.urban_management.modules.report.repository;

import com.smartcity.urban_management.modules.report.dto.ReportDetailResponse;
import com.smartcity.urban_management.modules.report.dto.ReportSummaryResponse;
import com.smartcity.urban_management.modules.report.entity.Report;
import com.smartcity.urban_management.modules.report.entity.ReportStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReportRepository extends
        JpaRepository<Report, UUID>,
        JpaSpecificationExecutor<Report> {
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
                    r.createdAt,
                    r.updatedAt
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

    /**
     * Lưu ý:
     * Hiện tại query này chỉ filter 2-3 field (status, category, keyword),
     * nên dùng @Query với param trực tiếp là nhanh và rõ ràng.
     * <p>
     * Nếu sau này có nhiều filter hơn (ví dụ >5-6 field) hoặc dynamic filter,
     * thì nên chuyển sang sử dụng Spring Data JPA Specification,
     * vì Specification sẽ giúp:
     * - Xây dựng query động dễ dàng
     * - Tái sử dụng filter logic
     * - Giảm việc viết nhiều @Query dài và phức tạp
     * <p>
     * Với số lượng filter ít, @Query + projection DTO vẫn là lựa chọn nhanh và tối ưu.
     */
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
            AND (:status IS NULL OR r.status = :status)
            AND (:category IS NULL OR r.category = :category)
            AND (
                :keyword IS NULL
                OR (r.title IS NOT NULL AND LOWER(r.title) LIKE LOWER(CONCAT('%', CAST(:keyword AS text), '%')))
                OR (r.description IS NOT NULL AND LOWER(r.description) LIKE LOWER(CONCAT('%', CAST(:keyword AS text), '%')))
            )
            """)
    Page<ReportSummaryResponse> findAllResponses(
            ReportStatus status,
            String category,
            String keyword,
            Pageable pageable
    );

    /**
     * Lưu ý:
     * Hiện tại query này chỉ filter 2-3 field (status, category, keyword),
     * nên dùng @Query với param trực tiếp là nhanh và rõ ràng.
     * <p>
     * Nếu sau này có nhiều filter hơn (ví dụ >5-6 field) hoặc dynamic filter,
     * thì nên chuyển sang sử dụng Spring Data JPA Specification,
     * vì Specification sẽ giúp:
     * - Xây dựng query động dễ dàng
     * - Tái sử dụng filter logic
     * - Giảm việc viết nhiều @Query dài và phức tạp
     * <p>
     * Với số lượng filter ít, @Query + projection DTO vẫn là lựa chọn nhanh và tối ưu.
     */
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
            AND u.id = :userId
            AND (:status IS NULL OR r.status = :status)
            AND (:category IS NULL OR r.category = :category)
            AND (
                :keyword IS NULL
                OR (r.title IS NOT NULL AND LOWER(r.title) LIKE LOWER(CONCAT('%', CAST(:keyword AS text), '%')))
                OR (r.description IS NOT NULL AND LOWER(r.description) LIKE LOWER(CONCAT('%', CAST(:keyword AS text), '%')))
            )
            """)
    Page<ReportSummaryResponse> findUserReports(
            UUID userId,
            ReportStatus status,
            String category,
            String keyword,
            Pageable pageable
    );

    Optional<Report> findByIdAndDeletedAtIsNull(UUID id);

    boolean existsByIdAndCreatedBy_Id(UUID id, UUID userId);

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
                AND u.id = :userId
                ORDER BY r.createdAt DESC
            """)
    List<ReportSummaryResponse> findRecentReports(UUID userId, Pageable pageable);
}