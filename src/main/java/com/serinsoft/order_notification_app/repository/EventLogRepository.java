package com.serinsoft.order_notification_app.repository;

import com.serinsoft.order_notification_app.entity.EventLog;
import com.serinsoft.order_notification_app.entity.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventLogRepository extends JpaRepository<EventLog, Long> {

    List<EventLog> findByTypeOrderByCreatedAtDesc(EventType type);
    List<EventLog> findByCreatedAtBetweenOrderByCreatedAtDesc(Long start, Long end);



}
