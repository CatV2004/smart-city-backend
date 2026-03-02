package com.smartcity.urban_management.modules.report.pagination;

import com.smartcity.urban_management.shared.pagination.SortResolver;

import java.util.Map;

public class ReportSortField implements SortResolver {

    private static final Map<String, String> SORT_FIELDS = Map.of(
            "createdAt", "createdAt",
            "status", "status",
            "title", "title",
            "category", "category"
    );

    @Override
    public String resolveField(String input) {
        return SORT_FIELDS.getOrDefault(input, defaultField());
    }

    @Override
    public String defaultField() {
        return "createdAt";
    }
}