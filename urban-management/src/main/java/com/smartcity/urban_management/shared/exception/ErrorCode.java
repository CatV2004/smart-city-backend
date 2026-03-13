package com.smartcity.urban_management.shared.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    // ===== SYSTEM =====
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SYS_500", "Internal server error"),

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

    // ===== REPORT =====
    REPORT_NOT_FOUND(HttpStatus.NOT_FOUND, "RPT_404", "Report not found"),
    INVALID_REPORT_STATUS(HttpStatus.BAD_REQUEST, "RPT_400_1", "Invalid report status transition"),
    REPORT_STATUS_CHANGE_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "RPT_400_2", "Status cannot be changed"),
    REPORT_ALREADY_DELETED(HttpStatus.BAD_REQUEST, "RPT_400_3", "Report already deleted"),
    REPORT_DELETE_NOT_ALLOWED(HttpStatus.FORBIDDEN, "RPT_403_1", "You are not allowed to delete this report"),
    REPORT_NOT_SOFT_DELETED(HttpStatus.BAD_REQUEST, "RPT_400_4", "Report must be soft deleted before hard delete"),

    // ===== FILE =====
    FILE_UPLOAD_FAILED(HttpStatus.BAD_GATEWAY, "FIL_502_1", "File upload failed"),
    FILE_DELETE_FAILED(HttpStatus.BAD_GATEWAY, "FIL_502_2", "File delete failed"),

    // ===== CATEGORY =====
    CATEGORY_SLUG_DUPLICATE(HttpStatus.CONFLICT, "CAT_409_1", "Category slug already exists"),
    CATEGORY_NAME_DUPLICATE(HttpStatus.CONFLICT, "CAT_409_2", "Category name already exists");


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