package com.serinsoft.order_notification_app.mapper;

import com.serinsoft.order_notification_app.dto.OrderItemResponse;
import com.serinsoft.order_notification_app.dto.OrderResponse;
import com.serinsoft.order_notification_app.entity.Order;
import com.serinsoft.order_notification_app.entity.OrderItem;

import java.util.List;

public final class OrderMapper {

    private OrderMapper() {
    }

    public static OrderResponse toResponse(Order order) {
        List<OrderItemResponse> items = order.getItems().stream()
                .map(OrderMapper::toItemResponse)
                .toList();

        return new OrderResponse(
                order.getId(),
                order.getOrderStatus(),
                order.getTotal(),
                order.getCreatedAt(),
                items
        );
    }

    private static OrderItemResponse toItemResponse(OrderItem item) {
        return new OrderItemResponse(
                item.getProductId(),
                item.getQuantity(),
                item.getUnitPrice(),
                item.getLineTotal()
        );
    }

}
