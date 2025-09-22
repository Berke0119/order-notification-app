package com.serinsoft.order_notification_app.service;

import com.serinsoft.order_notification_app.dto.OrderCreateRequest;
import com.serinsoft.order_notification_app.dto.OrderItemCreateRequest;
import com.serinsoft.order_notification_app.dto.OrderResponse;
import com.serinsoft.order_notification_app.dto.event.EmailMessage;
import com.serinsoft.order_notification_app.dto.event.Item;
import com.serinsoft.order_notification_app.dto.event.OrderCreateEvent;
import com.serinsoft.order_notification_app.dto.event.OrderStatusChangedEvent;
import com.serinsoft.order_notification_app.entity.Order;
import com.serinsoft.order_notification_app.entity.OrderItem;
import com.serinsoft.order_notification_app.entity.OrderStatus;
import com.serinsoft.order_notification_app.exception.NotFoundException;
import com.serinsoft.order_notification_app.mapper.OrderMapper;
import com.serinsoft.order_notification_app.producer.kafka.OrderEventsProducer;
import com.serinsoft.order_notification_app.producer.rabbit.EmailNotificationProducer;
import com.serinsoft.order_notification_app.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final OrderEventsProducer orderEventsProducer;
    private final EmailNotificationProducer emailNotificationProducer;

    public OrderService(OrderRepository orderRepository, ProductService productService, OrderEventsProducer orderEventsProducer, EmailNotificationProducer emailNotificationProducer) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.orderEventsProducer = orderEventsProducer;
        this.emailNotificationProducer = emailNotificationProducer;
    }


    public OrderResponse createOrder(OrderCreateRequest request){

        //Order total fiyatı için bir değişken tuttuk
        //orderRequest içindeki her bir item için liste oluşturduk
        List<OrderItem> items = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;

        for(OrderItemCreateRequest itReq : request.items()){

            var product = productService.getEntityById(itReq.productId());
            var unitPrice = product.getPrice();
            var quantity = itReq.quantity();

            var lineTotal = unitPrice.multiply(BigDecimal.valueOf(quantity));

            totalPrice = totalPrice.add(lineTotal);

            OrderItem item = new OrderItem(
                    product.getId(),
                    quantity,
                    unitPrice,
                    lineTotal
            );

            items.add(item);
        }

        //Order oluştur
        Order order = new Order(
                OrderStatus.CREATED,
                totalPrice,
                System.currentTimeMillis()
        );

        //iki yön bağlama
        for(OrderItem item : items){
            item.setOrder(order);
            order.getItems().add(item);
        }


        //Stoktan düş
        for(OrderItem item : items){
            productService.decreaseStock(item.getProductId(), item.getQuantity());
        }

        Order saved = orderRepository.save(order);

        //Oluşan order bilgisini event olarak publish et
        var itemsPayload = saved.getItems().stream().map((item) -> new Item(
                    item.getProductId(),
                    item.getQuantity(),
                    item.getUnitPrice(),
                    item.getLineTotal()
            )).toList();

        OrderCreateEvent event = new OrderCreateEvent(
                saved.getId(),
                saved.getTotal(),
                saved.getCreatedAt(),
                itemsPayload
        );

        orderEventsProducer.send("ORDER_CREATED",event);

        emailNotificationProducer.send("created",
                new EmailMessage("customer@example.com",
                        "Order Created #" + saved.getId(),
                        "Your order was created. Total: " + saved.getTotal())
        );

        return OrderMapper.toResponse(saved);
    }


    public OrderResponse updateStatus(Long orderId, OrderStatus newStatus){
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("Order with id "+ orderId +" not found"));
        OrderStatus oldStatus = order.getOrderStatus();
        order.setOrderStatus(newStatus);
        Order updated = orderRepository.save(order);

        // --- Publish: Kafka + Rabbit
        OrderStatusChangedEvent event = new OrderStatusChangedEvent(
                updated.getId(), oldStatus, newStatus, updated.getCreatedAt()
        );

        orderEventsProducer.send("ORDER_STATUS_CHANGED", event);

        emailNotificationProducer.send("status",
                new EmailMessage("customer@example.com",
                        "Order Status Changed #" + updated.getId(),
                        "New status: " + newStatus)
        );

        return OrderMapper.toResponse(updated);
    }


    public OrderResponse getById(Long id){
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order with id "+ id +" not found"));
        return OrderMapper.toResponse(order);
    }
}
