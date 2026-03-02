package com.smartcity.urban_management.shared.pagination;

public interface SortResolver {

    /**
     * Resolve client sort field -> entity field
     */
    String resolveField(String input);

    /**
     * default sort field if invalid
     */
    String defaultField();
}