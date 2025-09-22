package com.serinsoft.order_notification_app.listener.rabbit;

import com.serinsoft.order_notification_app.config.RabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationListener {

    @RabbitListener(queues = RabbitConfig.EMAIL_QUEUE)
    public void onEmailMessage(String json) {

        // Basit simülasyon: Burada gerçek e-posta göndermek yerine loglayacağız.
        System.out.println("[RabbitMQ] Simulated email send: " + json);
    }
}
