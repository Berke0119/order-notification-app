package com.serinsoft.order_notification_app.exception;

public record ApiFieldError(
        String field, String message
) {
    public static ApiFieldError of(String field, String message) {
        return new ApiFieldError(field, message);
    }
}
