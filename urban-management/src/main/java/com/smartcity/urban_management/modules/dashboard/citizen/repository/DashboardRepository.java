package com.smartcity.urban_management.modules.dashboard.citizen.repository;

import com.smartcity.urban_management.modules.dashboard.citizen.dto.CategoryCountDto;
import com.smartcity.urban_management.modules.dashboard.citizen.dto.ReportSummaryDto;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class DashboardRepository {

    private final EntityManager em;

    public ReportSummaryDto getCitizenSummary(UUID citizenId) {

        String jpql = """
                    SELECT new com.smartcity.urban_management.modules.dashboard.citizen.dto.ReportSummaryDto(
                        COUNT(r),
                        COALESCE(SUM(CASE WHEN r.status = 'PENDING' THEN 1 ELSE 0 END), 0),
                        COALESCE(SUM(CASE WHEN r.status IN (
                            'VERIFIED_AUTO',
                            'NEEDS_REVIEW',
                            'LOW_CONFIDENCE',
                            'ASSIGNED',
                            'IN_PROGRESS',
                            'VERIFIED'
                        ) THEN 1 ELSE 0 END), 0),
                        COALESCE(SUM(CASE WHEN r.status IN ('RESOLVED', 'CLOSED') THEN 1 ELSE 0 END), 0),
                        COALESCE(SUM(CASE WHEN r.status = 'REJECTED' THEN 1 ELSE 0 END), 0)
                    )
                    FROM Report r
                    WHERE r.createdBy.id = :citizenId
                    AND r.deletedAt IS NULL
                """;

        return em.createQuery(jpql, ReportSummaryDto.class)
                .setParameter("citizenId", citizenId)
                .getSingleResult();
    }

    public List<CategoryCountDto> getCategoryBreakdown(UUID citizenId) {

        String jpql = """
                    SELECT new com.smartcity.urban_management.modules.dashboard.citizen.dto.CategoryCountDto(
                        COALESCE(c.name, 'Chưa phân loại'),
                        COUNT(r)
                    )
                    FROM Report r
                    LEFT JOIN r.finalCategory c
                    WHERE r.createdBy.id = :citizenId
                    AND r.deletedAt IS NULL
                    GROUP BY c.name
                    ORDER BY COUNT(r) DESC
                """;

        return em.createQuery(jpql, CategoryCountDto.class)
                .setParameter("citizenId", citizenId)
                .getResultList();
    }
}
