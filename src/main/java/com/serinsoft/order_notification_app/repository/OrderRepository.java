package com.serinsoft.order_notification_app.repository;

import com.serinsoft.order_notification_app.entity.Order;
import com.serinsoft.order_notification_app.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByOrderStatusOrderByCreatedAtDesc(OrderStatus orderStatus);

}
