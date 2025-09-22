package com.serinsoft.order_notification_app.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiError(
        Long timestamp,
        int status,
        String error,
        String message,
        String path,
        List<ApiFieldError> fieldErrors
) {
    public static ApiError of(int status, String error, String message, String path) {
        return new ApiError(System.currentTimeMillis(), status, error, message, path, null);
    }
    public static ApiError of(int status, String error, String message, String path, List<ApiFieldError> fieldErrors) {
        return new ApiError(System.currentTimeMillis(), status, error, message, path, fieldErrors);
    }
}
