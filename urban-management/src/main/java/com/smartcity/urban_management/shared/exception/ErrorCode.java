package com.smartcity.urban_management.shared.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    // ===== SYSTEM =====
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SYS_500", "Internal server error"),

    // ===== GOOGLE MAPS =====
    GEOCODE_FAILED(HttpStatus.BAD_GATEWAY, "MAP_502_1", "Geocode failed"),
    GEOCODE_ZERO_RESULTS(HttpStatus.NOT_FOUND, "MAP_404_1", "Address not found"),
    GOOGLE_API_LIMIT(HttpStatus.TOO_MANY_REQUESTS, "MAP_429_1", "Google API quota exceeded"),
    GOOGLE_API_DENIED(HttpStatus.FORBIDDEN, "MAP_403_1", "Google API request denied"),

    // ===== REQUEST =====
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "REQ_400", "Invalid request"),

    // ===== AUTH =====
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "AUTH_401", "Unauthorized"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "AUTH_403", "Access denied"),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "AUTH_401_1", "Invalid identifier or password"),

    // ===== USER =====
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USR_404", "User not found"),

    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "USR_409_1", "Email already exists"),
    PHONE_ALREADY_EXISTS(HttpStatus.CONFLICT, "USR_409_2", "Phone number already exists"),

    USER_INACTIVE(HttpStatus.FORBIDDEN, "USR_403_1", "User account is inactive"),

    // ===== ROLE =====
    ROLE_NOT_FOUND(HttpStatus.NOT_FOUND, "ROLE_404", "Role not found"),
    INVALID_ROLE_ASSIGNMENT(HttpStatus.BAD_REQUEST, "ROLE_400", "Invalid role assignment"
    ),

    // ===== REPORT =====
    REPORT_NOT_FOUND(HttpStatus.NOT_FOUND, "RPT_404", "Report not found"),
    INVALID_REPORT_STATUS(HttpStatus.BAD_REQUEST, "RPT_400_1", "Invalid report status transition"),
    REPORT_STATUS_CHANGE_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "RPT_400_2", "Status cannot be changed"),
    REPORT_ALREADY_DELETED(HttpStatus.BAD_REQUEST, "RPT_400_3", "Report already deleted"),
    REPORT_DELETE_NOT_ALLOWED(HttpStatus.FORBIDDEN, "RPT_403_1", "You are not allowed to delete this report"),
    REPORT_NOT_SOFT_DELETED(HttpStatus.BAD_REQUEST, "RPT_400_4", "Report must be soft deleted before hard delete"),

    // ===== REPORT CATEGORY DECISION =====
    FINAL_CATEGORY_TYPE_INVALID(HttpStatus.BAD_REQUEST, "RPT_400_5", "Invalid final category type"),
    FINAL_CATEGORY_ID_REQUIRED(HttpStatus.BAD_REQUEST, "RPT_400_6", "CategoryId is required for MANUAL type"),
    FINAL_CATEGORY_NOT_AVAILABLE(HttpStatus.BAD_REQUEST, "RPT_400_7", "Selected category is not available for this report"),

    // ===== FILE =====
    FILE_UPLOAD_FAILED(HttpStatus.BAD_GATEWAY, "FIL_502_1", "File upload failed"),
    FILE_DELETE_FAILED(HttpStatus.BAD_GATEWAY, "FIL_502_2", "File delete failed"),

    // ===== CATEGORY =====
    CATEGORY_SLUG_DUPLICATE(HttpStatus.CONFLICT, "CAT_409_1", "Category slug already exists"),
    CATEGORY_NAME_DUPLICATE(HttpStatus.CONFLICT, "CAT_409_2", "Category name already exists"),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "CAT_404_1", "Category not found"),
    USER_CATEGORY_ISNULL(HttpStatus.NOT_FOUND, "CAT_400_1", "User category is null"),
    AI_CATEGORY_ISNULL(HttpStatus.NOT_FOUND, "CAT_400_2", "AI category is null"),

    // ===== DEPARTMENT =====
    DEPARTMENT_REQUIRED(HttpStatus.BAD_REQUEST, "DEPARTMENT_400_1", "Department is required for this role"),
    DEPARTMENT_CODE_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "DEPARTMENT_400_2", "Department code already exists"),
    DEPARTMENT_NAME_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "DEPARTMENT_400_4", "Department name already exists"),
    DEPARTMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "DEPARTMENT_404", "Department not found"),
    DEPARTMENT_HAS_CATEGORIES(HttpStatus.BAD_REQUEST, "DEPARTMENT_400_3", "Department has categories. Confirm deletion to remove all."),

    // ===== OFFICE =====
    USER_ALREADY_IN_OFFICE(HttpStatus.BAD_REQUEST, "OFFICE_400", "User already in office"),
    OFFICE_NOT_FOUND(HttpStatus.NOT_FOUND, "OFFICE_404", "Office not found"),
    OFFICE_REQUIRED(HttpStatus.BAD_REQUEST, "OFFICE_401", "Office is required for this role"),
    OFFICE_NOT_BELONG_TO_DEPARTMENT(HttpStatus.BAD_REQUEST, "OFFICE_402", "Office does not belong to the selected department");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}