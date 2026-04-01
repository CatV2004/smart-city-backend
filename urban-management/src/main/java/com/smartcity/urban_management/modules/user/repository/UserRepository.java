package com.smartcity.urban_management.modules.user.repository;

import com.smartcity.urban_management.modules.user.dto.UserDetailResponse;
import com.smartcity.urban_management.modules.user.dto.UserSummaryResponse;
import com.smartcity.urban_management.modules.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findById(UUID id);

    Optional<User> findByEmail(String email);

    @Query("""
                SELECT u FROM User u
                JOIN FETCH u.role
                WHERE u.email = :identifier
                   OR u.phoneNumber = :identifier
            """)
    Optional<User> findByEmailOrPhoneNumber(@Param("identifier") String identifier);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    @Query("SELECT u FROM User u JOIN FETCH u.role WHERE u.id = :id")
    Optional<User> findByIdWithRole(UUID id);

    @Query("""
                SELECT new com.smartcity.urban_management.modules.user.dto.UserDetailResponse(
                    u.id,
                    u.email,
                    u.fullName,
                    u.phoneNumber,
                    new com.smartcity.urban_management.modules.user.dto.RoleResponse(
                        r.id,
                        r.name
                    ),
                    d.code
                )
                FROM User u
                LEFT JOIN u.role r
                LEFT JOIN u.department d
                WHERE u.id = :id
            """)
    Optional<UserDetailResponse> findUserResponseById(UUID id);

    @Query("""
                SELECT new com.smartcity.urban_management.modules.user.dto.UserSummaryResponse(
                    u.id,
                    u.fullName,
                    u.email,
                    u.phoneNumber,
                    r.name,
                    d.code
                )
                FROM User u
                LEFT JOIN u.role r
                LEFT JOIN u.department d
                WHERE d.id = :departmentId
                AND u.deletedAt IS NULL
            """)
    Page<UserSummaryResponse> findByDepartment(
            @Param("departmentId") UUID departmentId,
            Pageable pageable
    );

    @Query("""
                SELECT new com.smartcity.urban_management.modules.user.dto.UserSummaryResponse(
                    u.id,
                    u.fullName,
                    u.email,
                    u.phoneNumber,
                    r.name,
                    d.code
                )
                FROM User u
                LEFT JOIN u.role r
                LEFT JOIN u.office o
                LEFT JOIN u.department d
                WHERE o.id = :officeId
                AND u.deletedAt IS NULL
            """)
    Page<UserSummaryResponse> findByOffice(
            @Param("officeId") UUID officeId,
            Pageable pageable
    );

    List<User> findByOfficeId(UUID officeId);

    @Query("""
                SELECT new com.smartcity.urban_management.modules.user.dto.UserSummaryResponse(
                    u.id,
                    u.fullName,
                    u.email,
                    u.phoneNumber,
                    r.name,
                    d.code
                )
                FROM User u
                LEFT JOIN u.role r
                LEFT JOIN u.department d
                WHERE
                    (:keyword IS NULL OR
                        LOWER(u.fullName) LIKE LOWER(CONCAT('%', CAST(:keyword AS text), '%')) OR
                        LOWER(u.email) LIKE LOWER(CONCAT('%', CAST(:keyword AS text), '%'))
                    )
                AND (:active IS NULL OR u.isActive = :active)
                AND (:departmentId IS NULL OR d.id = :departmentId)
                AND (:roleId IS NULL OR r.id = :roleId)
            """)
    Page<UserSummaryResponse> search(
            @Param("keyword") String keyword,
            @Param("active") Boolean active,
            @Param("departmentId") UUID departmentId,
            @Param("roleId") Long roleId,
            Pageable pageable
    );

    long countByDepartment_IdAndDeletedAtIsNull(UUID departmentId);

    @Query("""
                SELECT u.office.id
                FROM User u
                WHERE u.id = :userId
            """)
    UUID findDepartmentOfficeIdByUserId(@Param("userId") UUID userId);

    boolean existsByIdAndOfficeId(UUID userId, UUID officeId);

    @Query("SELECT u.id FROM User u WHERE u.role.name = 'ADMIN'")
    List<UUID> findAllAdminIds();
}