package com.smartcity.urban_management.modules.user.entity;

public enum RoleName {

    ADMIN,
    STAFF,
    CITIZEN;

    public static RoleName from(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Role is null");
        }
        return RoleName.valueOf(value.trim().toUpperCase());
    }

    public boolean isStaff() {
        return this == STAFF;
    }

    public boolean isAdmin() {
        return this == ADMIN;
    }
}