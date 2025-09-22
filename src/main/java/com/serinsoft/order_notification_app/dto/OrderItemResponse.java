package com.serinsoft.order_notification_app.dto;

import java.math.BigDecimal;

public record OrderItemResponse(
        Long productId,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal lineTotal
) {
}
