package com.weathernotification.weather_sns.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    DATABASE_ERROR(
            "ERR000",
            "An error occurred while trying to access database.",
            HttpStatus.INTERNAL_SERVER_ERROR),
    ID_MISSING("ERR001", "ID is missing", HttpStatus.BAD_REQUEST),
    USER_OBJECT_MISSING("ERR002", "User object is missing", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND("ERR003", "User not found with id %s", HttpStatus.BAD_REQUEST),
    USERNAME_MISSING("ERR004", "Username is missing", HttpStatus.BAD_REQUEST),
    EMAIL_MISSING("ERR005", "Email is missing", HttpStatus.BAD_REQUEST),
    PASSWORD_MISSING("ERR006", "Password is missing", HttpStatus.BAD_REQUEST),
    LOCATION_MISSING("ERR007", "Location is missing", HttpStatus.BAD_REQUEST),
    DUPLICATE_EMAIL("ERR008", "E-mail address already in use.", HttpStatus.CONFLICT),
    DUPLICATE_USERNAME("ERR009", "Username already in use.", HttpStatus.CONFLICT),
    USER_NOT_FOUND_USERNAME("ERR010", "User not found with username %s", HttpStatus.BAD_REQUEST),
    INVALID_CREDENTIALS("ERR011", "Invalid Credentals.", HttpStatus.UNAUTHORIZED);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getCode() {
        return code;
    }

    public String getMessage(Object... args) {
        return String.format(message, args);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
