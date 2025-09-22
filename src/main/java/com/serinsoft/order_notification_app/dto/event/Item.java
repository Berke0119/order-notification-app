package com.serinsoft.order_notification_app.dto.event;

import java.math.BigDecimal;

public record Item(
        Long productId,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal lineTotal
) {
}
