package com.smartcity.urban_management.modules.location.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MapDataResponse {
    private String type = "FeatureCollection";
    private List<MapFeatureResponse> features;
}