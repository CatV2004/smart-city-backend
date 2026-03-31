package com.smartcity.urban_management.modules.location.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MapboxMatrixResponse {
    private List<List<Double>> durations;
}
