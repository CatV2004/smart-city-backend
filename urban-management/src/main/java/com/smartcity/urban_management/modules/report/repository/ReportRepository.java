package com.smartcity.urban_management.modules.report.repository;

import com.smartcity.urban_management.modules.location.dto.projection.ReportMapProjection;
import com.smartcity.urban_management.modules.report.dto.AttachmentProjection;
import com.smartcity.urban_management.modules.report.dto.detail.ReportDetailProjection;
import com.smartcity.urban_management.modules.report.dto.summary.ReportSummaryProjection;
import com.smartcity.urban_management.modules.report.entity.Report;
import com.smartcity.urban_management.modules.report.entity.ReportStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface ReportRepository extends
        JpaRepository<Report, UUID>,
        JpaSpecificationExecutor<Report> {

    @Query("""
                SELECT
                    r.id as id,
                    r.title as title,
                    r.description as description,
            
                    uc.name as userCategoryName,
                    ac.name as aiCategoryName,
                    fc.name as finalCategoryName,
            
                    r.aiConfidence as aiConfidence,
            
                    r.status as status,
            
                    cast(function('ST_Y', r.location) as double) as latitude,
                    cast(function('ST_X', r.location) as double) as longitude,
            
                    r.address as address,
            
                    u.fullName as createdByName,
                    u.id as createdByUserId,
            
                    ap.fullName as approvedByName,
                    ap.id as approvedById,
            
                    r.createdAt as createdAt,
                    r.updatedAt as updatedAt
            
                FROM Report r
                JOIN r.createdBy u
                LEFT JOIN r.approvedBy ap
                LEFT JOIN r.userCategory uc
                LEFT JOIN r.aiCategory ac
                LEFT JOIN r.finalCategory fc
            
                WHERE r.id = :id AND r.deletedAt IS NULL
            """)
    Optional<ReportDetailProjection> findDetailProjection(UUID id);

    @Query("""
                SELECT
                    r.id as id,
                    r.title as title,
                    r.description as description,
                    uc.name as userCategoryName,
                    ac.name as aiCategoryName,
                    fc.name as finalCategoryName,
                    r.aiConfidence as aiConfidence,
                    r.priority as priority,
                    r.status as status,
                    cast(function('ST_Y', r.location) as double) as latitude,
                    cast(function('ST_X', r.location) as double) as longitude,
                    r.address as address,
                    u.fullName as createdByName,
                    u.id as createdByUserId,
                    r.createdAt as createdAt
                FROM Report r
                JOIN r.createdBy u
                LEFT JOIN r.userCategory uc
                LEFT JOIN r.aiCategory ac
                LEFT JOIN r.finalCategory fc
                WHERE r.deletedAt IS NULL
                AND (:statuses IS NULL OR r.status IN (:statuses))
                AND (:categoryId IS NULL OR (uc.id = :categoryId OR ac.id = :categoryId OR fc.id = :categoryId))
                AND (
                    :keyword IS NULL
                    OR (r.title IS NOT NULL AND LOWER(r.title) LIKE LOWER(CONCAT('%', CAST(:keyword AS text), '%')))
                    OR (r.description IS NOT NULL AND LOWER(r.description) LIKE LOWER(CONCAT('%', CAST(:keyword AS text), '%')))
                )
            """)
    Page<ReportSummaryProjection> findAllSummary(
            @Param("statuses") Set<ReportStatus> statuses,
            @Param("categoryId") UUID categoryId,
            @Param("keyword") String keyword,
            Pageable pageable
    );

    @Query("""
                SELECT
                    r.id as id,
                    r.title as title,
                    r.description as description,
                    uc.name as userCategoryName,
                    ac.name as aiCategoryName,
                    fc.name as finalCategoryName,
                    r.aiConfidence as aiConfidence,
                    r.priority as priority,
                    r.status as status,
                    cast(function('ST_Y', r.location) as double) as latitude,
                    cast(function('ST_X', r.location) as double) as longitude,
                    r.address as address,
                    u.fullName as createdByName,
                    u.id as createdByUserId,
                    r.createdAt as createdAt
                FROM Report r
                JOIN r.createdBy u
                LEFT JOIN r.userCategory uc
                LEFT JOIN r.aiCategory ac
                LEFT JOIN r.finalCategory fc
                WHERE r.deletedAt IS NULL
                AND u.id = :userId
                AND (:statusList IS NULL OR r.status IN :statusList)
                AND (:categoryId IS NULL OR (uc.id = :categoryId OR ac.id = :categoryId OR fc.id = :categoryId))
                AND (
                    :keyword IS NULL
                    OR (r.title IS NOT NULL AND LOWER(r.title) LIKE LOWER(CONCAT('%', CAST(:keyword AS text), '%')))
                    OR (r.description IS NOT NULL AND LOWER(r.description) LIKE LOWER(CONCAT('%', CAST(:keyword AS text), '%')))
                )
            """)
    Page<ReportSummaryProjection> findUserReportSummary(
            UUID userId,
            @Param("statusList") List<ReportStatus> statusList,
            @Param("categoryId") UUID categoryId,
            @Param("keyword") String keyword,
            Pageable pageable
    );

    @Query("""
                SELECT
                    r.id as id,
                    r.title as title,
                    r.description as description,
                    uc.name as userCategoryName,
                    ac.name as aiCategoryName,
                    fc.name as finalCategoryName,
                    r.aiConfidence as aiConfidence,
                    r.priority as priority,
                    r.status as status,
                    cast(function('ST_Y', r.location) as double) as latitude,
                    cast(function('ST_X', r.location) as double) as longitude,
                    r.address as address,
                    u.fullName as createdByName,
                    u.id as createdByUserId,
                    r.createdAt as createdAt
                FROM Report r
                JOIN r.createdBy u
                LEFT JOIN r.userCategory uc
                LEFT JOIN r.aiCategory ac
                LEFT JOIN r.finalCategory fc
                WHERE r.deletedAt IS NULL
                AND u.id = :userId
                ORDER BY r.createdAt DESC
            """)
    List<ReportSummaryProjection> findRecentSummary(UUID userId, Pageable pageable);

    @Query("""
                SELECT
                    r.id as id,
                    r.title as title,
                    r.description as description,
                    uc.name as userCategoryName,
                    ac.name as aiCategoryName,
                    fc.name as finalCategoryName,
                    r.aiConfidence as aiConfidence,
                    r.priority as priority,
                    r.status as status,
                    cast(function('ST_Y', r.location) as double) as latitude,
                    cast(function('ST_X', r.location) as double) as longitude,
                    r.address as address,
                    u.fullName as createdByName,
                    u.id as createdByUserId,
                    r.createdAt as createdAt
                FROM Report r
                JOIN r.createdBy u
                LEFT JOIN r.userCategory uc
                LEFT JOIN r.aiCategory ac
                LEFT JOIN r.finalCategory fc
                WHERE r.id = :id AND r.deletedAt IS NULL
            """)
    Optional<ReportSummaryProjection> findSummaryProjectionById(UUID id);

    Optional<Report> findByIdAndDeletedAtIsNull(UUID id);

    boolean existsByIdAndCreatedBy_Id(UUID id, UUID userId);

    // Admin Dashboard
    Integer countByStatusAndDeletedAtIsNull(ReportStatus status);

    Integer countByStatusInAndDeletedAtIsNull(List<ReportStatus> statuses);

    Integer countByStatusAndCreatedAtBetweenAndDeletedAtIsNull(
            ReportStatus status,
            LocalDateTime start,
            LocalDateTime end
    );

    Integer countByStatusInAndCreatedAtBetweenAndDeletedAtIsNull(
            List<ReportStatus> statuses,
            LocalDateTime start,
            LocalDateTime end
    );

    Integer countByDeletedAtIsNull();

    // Query methods for priority reports
    Page<Report> findByStatusInAndDeletedAtIsNull(List<ReportStatus> statuses, Pageable pageable);

    // Activity methods
    List<Report> findTop5ByStatusAndDeletedAtIsNullOrderByUpdatedAtDesc(ReportStatus status);

    List<Report> findTop5ByStatusInAndDeletedAtIsNullOrderByUpdatedAtDesc(List<ReportStatus> statuses);

    @Query(value = """
                SELECT
                    r.id,
                    r.title,
                    r.description,
                    r.status,
                    r.priority,
                    r.address,
                    r.ai_confidence as aiConfidence,
                    COALESCE(fc.name, ac.name, uc.name) as categoryName,
                    ST_Y(r.location) as lat,
                    ST_X(r.location) as lng,
                    r.created_at as createdAt,
                    r.updated_at as updatedAt
                FROM reports r
                LEFT JOIN categories fc ON r.final_category_id = fc.id
                LEFT JOIN categories ac ON r.ai_category_id = ac.id
                LEFT JOIN categories uc ON r.user_category_id = uc.id
                WHERE r.deleted_at IS NULL
            """, nativeQuery = true)
    List<ReportMapProjection> findAllForMap();

    @Query(value = """
                SELECT
                    r.id,
                    r.title,
                    r.description,
                    r.status,
                    r.priority,
                    r.address,
                    r.ai_confidence as aiConfidence,
                    COALESCE(fc.name, ac.name, uc.name) as categoryName,
                    ST_Y(r.location) as lat,
                    ST_X(r.location) as lng,
                    r.created_at as createdAt,
                    r.updated_at as updatedAt
            
                FROM reports r
                LEFT JOIN categories fc ON r.final_category_id = fc.id
                LEFT JOIN categories uc ON r.user_category_id = uc.id
                LEFT JOIN categories ac ON r.ai_category_id = ac.id
            
                WHERE r.deleted_at IS NULL
            
                AND (
                    r.status IN (:statuses)
                )
            
                AND (
                    :categoryIds IS NULL OR
                    COALESCE(r.final_category_id, r.user_category_id, r.ai_category_id)
                    IN (:categoryIds)
                )
            
                AND (
                    :keyword IS NULL OR
                    LOWER(r.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
                    LOWER(r.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
                    LOWER(r.address) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
                    LOWER(COALESCE(fc.name, uc.name, ac.name)) LIKE LOWER(CONCAT('%', :keyword, '%'))
                )
            """, nativeQuery = true)
    List<ReportMapProjection> findAllForMapFiltered(
            @Param("statuses") List<String> statuses,
            @Param("categoryIds") List<UUID> categoryIds,
            @Param("keyword") String keyword
    );

    @Query(value = """
                SELECT
                    a.report_id as reportId,
                    a.file_url as fileUrl
                FROM attachments a
                WHERE a.report_id IN :reportIds
            """, nativeQuery = true)
    List<AttachmentProjection> findAttachmentsByReportIds(List<UUID> reportIds);
}