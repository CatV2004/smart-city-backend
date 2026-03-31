package com.smartcity.urban_management.modules.location.client;

import com.smartcity.urban_management.modules.department.entity.DepartmentOffice;
import com.smartcity.urban_management.modules.location.dto.MapboxMatrixResponse;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MapboxClient implements RoutingClient {

    @Value("${mapbox.access-token}")
    private String token;

    private final RestTemplate restTemplate;

    @Override
    public List<Double> getDurations(Point origin, List<DepartmentOffice> offices) {

        StringBuilder coords = new StringBuilder();

        // origin
        coords.append(origin.getX()).append(",").append(origin.getY());

        for (DepartmentOffice o : offices) {
            coords.append(";")
                    .append(o.getLocation().getX())
                    .append(",")
                    .append(o.getLocation().getY());
        }

        String url = String.format(
                "https://api.mapbox.com/directions-matrix/v1/mapbox/driving/%s?access_token=%s",
                coords,
                token
        );

        MapboxMatrixResponse res =
                restTemplate.getForObject(url, MapboxMatrixResponse.class);

        if (res == null || res.getDurations() == null) {
            throw new RuntimeException("Mapbox response invalid");
        }

        return res.getDurations().get(0);
    }
}