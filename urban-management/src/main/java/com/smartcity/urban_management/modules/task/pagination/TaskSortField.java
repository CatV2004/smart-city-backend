package com.smartcity.urban_management.modules.task.pagination;

import com.smartcity.urban_management.shared.pagination.SortResolver;

import java.util.Map;


public class TaskSortField implements SortResolver {

    private static final Map<String, String> SORT_FIELDS = Map.of(
            "createdAt", "createdAt",
            "assignedAt", "assignedAt"
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