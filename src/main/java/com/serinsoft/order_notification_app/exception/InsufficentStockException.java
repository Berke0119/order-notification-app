package com.serinsoft.order_notification_app.exception;

public class InsufficentStockException extends RuntimeException{
    public InsufficentStockException(String message) {
        super(message);
    }
}
