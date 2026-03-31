package com.smartcity.urban_management.modules.location.service;

import com.smartcity.urban_management.modules.location.dto.MapDataResponse;
import com.smartcity.urban_management.modules.location.dto.MapFilterRequest;

import java.util.UUID;

public interface MapService {
    MapDataResponse getMapData(MapFilterRequest filter);

    MapDataResponse getTaskMapDataForStaff(MapFilterRequest filter, UUID userId);
}