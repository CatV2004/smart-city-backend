package com.smartcity.urban_management.modules.location.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MapRealtimeMessage {

    /**
     * CREATE | UPDATE | DELETE
     */
    private String action;

    /**
     * GeoJSON Feature
     */
    private MapFeatureResponse feature;
}