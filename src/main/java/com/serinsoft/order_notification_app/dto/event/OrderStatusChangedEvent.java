package com.serinsoft.order_notification_app.dto.event;

import com.serinsoft.order_notification_app.entity.OrderStatus;

public record OrderStatusChangedEvent(
        Long orderId,
        OrderStatus oldStatus,
        OrderStatus newStatus,
        Long changedAt
) {
}
