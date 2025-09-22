package com.serinsoft.order_notification_app.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderItemCreateRequest(
        @NotNull Long productId,
        @NotNull @Min(1) Integer quantity
) {
}
