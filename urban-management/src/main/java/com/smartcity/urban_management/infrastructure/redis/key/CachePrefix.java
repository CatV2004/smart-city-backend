package com.smartcity.urban_management.infrastructure.redis.key;

public enum CachePrefix {

    REPORT("report"),
    CATEGORY("category"),
    DEPARTMENT("department"),
    USER("user"),
    MAP("map"),
    TASK("task"),
    OFFICE("office");

    private final String value;

    CachePrefix(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}