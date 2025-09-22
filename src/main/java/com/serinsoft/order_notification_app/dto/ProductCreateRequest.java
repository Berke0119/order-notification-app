package com.serinsoft.order_notification_app.dto;

import com.serinsoft.order_notification_app.entity.Product;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductCreateRequest(
        @NotBlank @Size(max = 120) String name,
        @NotNull @DecimalMin(value = "0.0", inclusive = false) BigDecimal price,
        @NotNull @Min(0) Integer stock
) {
}
