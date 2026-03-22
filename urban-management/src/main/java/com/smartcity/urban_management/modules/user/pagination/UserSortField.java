package com.smartcity.urban_management.modules.user.pagination;

import com.smartcity.urban_management.shared.pagination.SortResolver;

import java.util.Map;

public class UserSortField implements SortResolver {

    private static final Map<String, String> SORT_FIELDS = Map.of(
            "createdAt", "createdAt"
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