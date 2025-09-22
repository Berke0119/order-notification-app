package com.serinsoft.order_notification_app.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    public static final String ORDER_EVENTS_TOPIC = "order.events";

    @Bean
    public NewTopic orderEventsTopic() {
        return new NewTopic(ORDER_EVENTS_TOPIC, 3, (short) 1);
    }

}
