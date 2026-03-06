package com.smartcity.urban_management.modules.dashboard.controller;

import com.smartcity.urban_management.modules.dashboard.dto.CitizenDashboardResponse;
import com.smartcity.urban_management.modules.dashboard.service.CitizenDashboardService;
import com.smartcity.urban_management.security.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/citizen/dashboard")
@RequiredArgsConstructor
public class CitizenDashboardController {

    private final CitizenDashboardService dashboardService;

    @GetMapping
    public CitizenDashboardResponse getDashboard(
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return dashboardService.getDashboard(user.getId());
    }
}
