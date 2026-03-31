package com.smartcity.urban_management.modules.task.repository;

import com.smartcity.urban_management.modules.location.dto.projection.TaskMapProjection;
import com.smartcity.urban_management.modules.task.dto.TaskDetailProjection;
import com.smartcity.urban_management.modules.task.dto.TaskProjection;
import com.smartcity.urban_management.modules.task.entity.Task;
import com.smartcity.urban_management.modules.task.entity.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    @Query("""
                SELECT
                    t.id as id,
                    t.status as status,
                    t.assignedAt as assignedAt,
                    t.startedAt as startedAt,
                    t.completedAt as completedAt,
                    r.id as reportId,
                    u.id as assignedUserId,
                    u.fullName as assignedUserName
                FROM Task t
                JOIN t.report r
                LEFT JOIN t.assignedUser u
                WHERE (:officeId IS NULL OR t.departmentOffice.id = :officeId)
                  AND (:status IS NULL OR t.status = :status)
                  AND (:keyword IS NULL OR LOWER(t.note) LIKE LOWER(CONCAT('%', CAST(:keyword AS text), '%')))
                  AND t.deletedAt IS NULL
            """)
    Page<TaskProjection> searchProjection(
            @Param("officeId") UUID officeId,
            @Param("status") TaskStatus status,
            @Param("keyword") String keyword,
            Pageable pageable
    );

    @Query("""
                SELECT
                    t.id as id,
                    t.status as status,
                    t.note as note,
                    t.assignedAt as assignedAt,
                    t.startedAt as startedAt,
                    t.completedAt as completedAt,
            
                    u.id as assignedUserId,
                    u.fullName as assignedUserName,
            
                    o.id as departmentOfficeId,
                    o.name as departmentOfficeName,
            
                    r.id as reportId,
                    r.title as reportTitle,
                    r.description as reportDescription,
                    r.address as address,
                    cast(function('ST_Y', r.location) as double) as latitude,
                    cast(function('ST_X', r.location) as double) as longitude,
                    r.createdAt as reportCreatedAt,
                    r.updatedAt as reportUpdatedAt,
                    r.status as reportStatus,
            
                    approver.fullName as approvedByName,
            
                    creator.fullName as createdByName,
                    creator.id as createdByUserId
            
                FROM Task t
                JOIN t.report r
                LEFT JOIN t.assignedUser u
                LEFT JOIN t.departmentOffice o
                LEFT JOIN r.createdBy creator
                LEFT JOIN r.approvedBy approver
            
                WHERE t.id = :id
            """)
    Optional<TaskDetailProjection> findDetail(UUID id);

    boolean existsByReportId(UUID reportId);

    Optional<Task> findByReportId(UUID id);

    @Query("""
                SELECT t.departmentOffice.id
                FROM Task t
                WHERE t.id = :taskId
            """)
    UUID findOfficeIdByTaskId(UUID taskId);

    @Query("""
                SELECT
                    CAST(t.id as string) as id,
                    CAST(t.status as string) as status,
            
                    u.fullName as assignedUserName,
            
                    r.id as reportId,
                    r.title as reportTitle,
                    r.description as reportDescription,
                    r.address as reportAddress,
                    ST_Y(r.location) as reportLat,
                    ST_X(r.location) as reportLng,
            
                    t.assignedAt as assignedAt,
                    t.startedAt as startedAt,
                    t.completedAt as completedAt
            
                FROM Task t
                JOIN t.report r
                LEFT JOIN t.assignedUser u
                JOIN t.departmentOffice o
                JOIN User me ON me.office.id = o.id
            
                WHERE me.id = :userId
                  AND (:statuses IS NULL OR t.status IN :statuses)
                  AND (:keyword IS NULL OR LOWER(r.title) LIKE LOWER(CONCAT('%', CAST(:keyword AS text), '%')))
            """)
    List<TaskMapProjection> findAllForMapByUser(
            UUID userId,
            List<String> statuses,
            String keyword
    );

}
