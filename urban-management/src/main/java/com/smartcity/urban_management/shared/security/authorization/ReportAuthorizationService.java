package com.smartcity.urban_management.shared.security.authorization;

import com.smartcity.urban_management.modules.report.repository.ReportRepository;
import com.smartcity.urban_management.security.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("reportAuth")
@RequiredArgsConstructor
public class ReportAuthorizationService {

    private final ReportRepository reportRepository;

    public boolean canDelete(UUID reportId,
                             Authentication authentication) {

        CustomUserDetails user =
                (CustomUserDetails) authentication.getPrincipal();

        // ADMIN luôn được phép
        boolean isAdmin = user.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            return true;
        }

        // load owner id (query nhẹ)
        return reportRepository
                .existsByIdAndCreatedBy_Id(
                        reportId,
                        user.getId()
                );
    }
}
