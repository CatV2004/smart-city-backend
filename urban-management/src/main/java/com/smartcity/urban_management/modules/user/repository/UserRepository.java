package com.smartcity.urban_management.modules.user.repository;

import com.smartcity.urban_management.modules.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    Optional<User> findByPhoneNumber(String phoneNumber);

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
}