package com.smartcity.urban_management.modules.location.service;

import com.smartcity.urban_management.modules.location.dto.MapDataResponse;
import com.smartcity.urban_management.modules.location.dto.MapFilterRequest;

public interface MapService {
    MapDataResponse getMapData(MapFilterRequest filter);
}