package com.serinsoft.order_notification_app.dto;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record OrderCreateRequest(
        @NotEmpty List<OrderItemCreateRequest> items
) {
}
