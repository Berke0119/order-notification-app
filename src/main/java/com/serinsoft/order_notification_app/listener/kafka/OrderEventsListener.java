package com.serinsoft.order_notification_app.listener.kafka;

import com.serinsoft.order_notification_app.config.KafkaConfig;
import com.serinsoft.order_notification_app.entity.EventLog;
import com.serinsoft.order_notification_app.entity.EventType;
import com.serinsoft.order_notification_app.repository.EventLogRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventsListener {

    private final EventLogRepository eventLogRepository;


    public OrderEventsListener(EventLogRepository eventLogRepository) {
        this.eventLogRepository = eventLogRepository;
    }


    @KafkaListener(topics = KafkaConfig.ORDER_EVENTS_TOPIC, groupId = "order-notify-consumers")
    public void onMessage(String message){

        EventType type = message.contains("\"oldStatus\"") ? EventType.ORDER_STATUS_CHANGED : EventType.ORDER_CREATED;

        EventLog log = new EventLog(
                type,
                message,
                System.currentTimeMillis()
        );

        eventLogRepository.save(log);
    }
}
