package com.serinsoft.order_notification_app.controller;

import com.serinsoft.order_notification_app.dto.OrderCreateRequest;
import com.serinsoft.order_notification_app.dto.OrderResponse;
import com.serinsoft.order_notification_app.entity.OrderStatus;
import com.serinsoft.order_notification_app.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/orders")
@Validated
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderCreateRequest request) {
        OrderResponse created = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }


    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getById(id));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(@PathVariable Long id, @RequestParam("value") OrderStatus status) {
        OrderResponse updated = orderService.updateStatus(id, status);
        return ResponseEntity.ok(updated);
    }
}
