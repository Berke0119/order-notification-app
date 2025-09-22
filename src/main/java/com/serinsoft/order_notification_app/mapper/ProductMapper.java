package com.serinsoft.order_notification_app.mapper;

import com.serinsoft.order_notification_app.dto.ProductCreateRequest;
import com.serinsoft.order_notification_app.dto.ProductResponse;
import com.serinsoft.order_notification_app.entity.Product;

public final class ProductMapper {

    private ProductMapper() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static Product toEntity(ProductCreateRequest request) {
        return new Product(
                null,
                request.name(),
                request.price(),
                request.stock()
        );
    }

    public static ProductResponse toResponse(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getStock());
    }

}
