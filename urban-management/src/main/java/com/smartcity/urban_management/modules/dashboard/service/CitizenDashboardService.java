package com.smartcity.urban_management.modules.dashboard.service;

import com.smartcity.urban_management.modules.dashboard.dto.CitizenDashboardResponse;

import java.util.UUID;

public interface CitizenDashboardService {

    CitizenDashboardResponse getDashboard(UUID citizenId);

}
