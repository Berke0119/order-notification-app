package com.serinsoft.order_notification_app.dto.event;

import java.math.BigDecimal;
import java.util.List;

public record OrderCreateEvent(
        Long orderId,
        BigDecimal total,
        Long createdAt,
        List<Item> items
) {
}


