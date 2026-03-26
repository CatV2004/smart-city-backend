package com.smartcity.urban_management.modules.location.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MapFeatureResponse {
    private String type = "Feature";
    private Map<String, Object> properties;
    private Geometry geometry;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Geometry {
        private String type = "Point";
        private double[] coordinates;
    }
}