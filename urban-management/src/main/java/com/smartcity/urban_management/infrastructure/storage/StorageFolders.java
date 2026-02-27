package com.smartcity.urban_management.infrastructure.storage;

public final class StorageFolders {

    private StorageFolders() {}

    public static String reportFolder(String reportId) {
        return "urban-management/reports/" + reportId;
    }
}