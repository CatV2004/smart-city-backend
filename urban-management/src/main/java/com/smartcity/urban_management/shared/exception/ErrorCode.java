package com.smartcity.urban_management.shared.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SYS_500", "Internal server error"),

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "REQ_400", "Invalid request"),

    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "AUTH_401", "Unauthorized"),

    FORBIDDEN(HttpStatus.FORBIDDEN, "AUTH_403", "Access denied"),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USR_404", "User not found"),

    REPORT_NOT_FOUND(HttpStatus.NOT_FOUND, "RPT_404", "Report not found");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getStatus() { return status; }
    public String getCode() { return code; }
    public String getMessage() { return message; }
}