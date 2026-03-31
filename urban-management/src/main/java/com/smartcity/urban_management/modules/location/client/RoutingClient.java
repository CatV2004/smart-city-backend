package com.smartcity.urban_management.modules.location.client;

import com.smartcity.urban_management.modules.department.entity.DepartmentOffice;
import org.locationtech.jts.geom.Point;

import java.util.List;

public interface RoutingClient {

    /**
     * @param origin        điểm xuất phát (report)
     * @param destinations  danh sách office
     * @return durations (seconds) từ origin → từng destination
     */
    List<Double> getDurations(Point origin, List<DepartmentOffice> destinations);
}