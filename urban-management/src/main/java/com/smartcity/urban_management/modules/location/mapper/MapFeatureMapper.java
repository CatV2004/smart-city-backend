package com.smartcity.urban_management.modules.location.mapper;

import com.smartcity.urban_management.modules.category.entity.Category;
import com.smartcity.urban_management.modules.location.dto.MapFeatureResponse;
import com.smartcity.urban_management.modules.report.entity.Report;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MapFeatureMapper {

    public MapFeatureResponse toReportFeature(
            Report report
    ) {

        Map<String, Object> properties = new HashMap<>();

        properties.put("id", report.getId());
        properties.put("type", "report");

        properties.put("title", report.getTitle());
        properties.put("description", report.getDescription());

        properties.put("status", report.getStatus());

        properties.put("priority", report.getPriority());

        properties.put("address", report.getAddress());

        properties.put("aiConfidence", report.getAiConfidence());

        properties.put("createdAt", report.getCreatedAt());
        properties.put("updatedAt", report.getUpdatedAt());

        Category category =
                resolveCategory(report);

        if (category != null) {

            properties.put(
                    "category",
                    category.getName()
            );

            properties.put(
                    "categoryId",
                    category.getId()
            );
        }

        Point point = report.getLocation();

        return new MapFeatureResponse(
                "Feature",
                properties,
                new MapFeatureResponse.Geometry(
                        "Point",
                        new double[]{
                                point.getX(),
                                point.getY()
                        }
                )
        );
    }

    /**
     * finalCategory > aiCategory > userCategory
     */
    private Category resolveCategory(
            Report report
    ) {

        if (report.getFinalCategory() != null) {
            return report.getFinalCategory();
        }

        if (report.getAiCategory() != null) {
            return report.getAiCategory();
        }

        return report.getUserCategory();
    }
}