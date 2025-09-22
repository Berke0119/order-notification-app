package com.serinsoft.order_notification_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class OrderNotificationAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderNotificationAppApplication.class, args);
	}

}
