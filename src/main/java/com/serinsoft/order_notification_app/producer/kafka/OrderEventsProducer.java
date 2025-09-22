package com.serinsoft.order_notification_app.producer.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.serinsoft.order_notification_app.config.KafkaConfig;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderEventsProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public OrderEventsProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void send(String key, Object payload){
        try {
            String message = objectMapper.writeValueAsString(payload);
            kafkaTemplate.send(KafkaConfig.ORDER_EVENTS_TOPIC, key, message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize Kafka payload", e);
        }
    }
}
