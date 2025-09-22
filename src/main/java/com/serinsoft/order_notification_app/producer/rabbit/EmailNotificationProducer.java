package com.serinsoft.order_notification_app.producer.rabbit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serinsoft.order_notification_app.config.RabbitConfig;
import com.serinsoft.order_notification_app.dto.event.EmailMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationProducer {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public EmailNotificationProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void send(String routingSuffix, EmailMessage message) {
        try {
            String json = objectMapper.writeValueAsString(message);
            String routingKey = RabbitConfig.EMAIL_ROUTING_KEY_PREFIX + routingSuffix; // email.created / email.status
            rabbitTemplate.convertAndSend(RabbitConfig.NOTIFICATIONS_EXCHANGE, routingKey, json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize RabbitMQ payload", e);
        }
    }

}
