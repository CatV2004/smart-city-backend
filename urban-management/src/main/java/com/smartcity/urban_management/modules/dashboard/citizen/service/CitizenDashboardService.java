package com.smartcity.urban_management.modules.dashboard.citizen.service;

import com.smartcity.urban_management.modules.dashboard.citizen.dto.CitizenDashboardResponse;

import java.util.UUID;

public interface CitizenDashboardService {

    CitizenDashboardResponse getDashboard(UUID citizenId);

}
