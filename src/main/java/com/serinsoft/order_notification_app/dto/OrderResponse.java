package com.serinsoft.order_notification_app.dto;

import com.serinsoft.order_notification_app.entity.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

public record OrderResponse(
        Long id,
        OrderStatus orderStatus,
        BigDecimal total,
        Long createdAt,
        List<OrderItemResponse> items
) {
}
