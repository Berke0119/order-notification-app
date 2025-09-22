package com.serinsoft.order_notification_app.dto;

import com.serinsoft.order_notification_app.entity.Product;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String name,
        BigDecimal price,
        Integer stock
) {
}
