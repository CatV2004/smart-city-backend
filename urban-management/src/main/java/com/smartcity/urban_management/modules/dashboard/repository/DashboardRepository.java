package com.smartcity.urban_management.modules.dashboard.repository;

import com.smartcity.urban_management.modules.dashboard.dto.ReportSummaryDto;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class DashboardRepository {

    private final EntityManager em;

    public ReportSummaryDto getCitizenSummary(UUID citizenId) {

        String jpql = """
            SELECT new com.smartcity.urban_management.modules.dashboard.dto.ReportSummaryDto(
                COUNT(r),
                SUM(CASE WHEN r.status = 'PENDING' THEN 1 ELSE 0 END),
                SUM(CASE WHEN r.status = 'IN_PROGRESS' THEN 1 ELSE 0 END),
                SUM(CASE WHEN r.status = 'RESOLVED' THEN 1 ELSE 0 END),
                SUM(CASE WHEN r.status = 'REJECTED' THEN 1 ELSE 0 END)
            )
            FROM Report r
            WHERE r.createdBy.id = :citizenId
            AND r.deletedAt IS NULL
        """;

        return em.createQuery(jpql, ReportSummaryDto.class)
                .setParameter("citizenId", citizenId)
                .getSingleResult();
    }
}
