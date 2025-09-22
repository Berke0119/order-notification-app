package com.serinsoft.order_notification_app.service;

import com.serinsoft.order_notification_app.dto.EventLogResponse;
import com.serinsoft.order_notification_app.entity.EventType;
import com.serinsoft.order_notification_app.mapper.EventLogMapper;
import com.serinsoft.order_notification_app.repository.EventLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventLogService {

    public final EventLogRepository eventLogRepository;

    public EventLogService(EventLogRepository eventLogRepository) {
        this.eventLogRepository = eventLogRepository;
    }

    public List<EventLogResponse> listAllDesc(){
        return eventLogRepository.findAll().stream()
                .sorted((a,b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
                .map(EventLogMapper::toResponse)
                .toList();
    }

    public List<EventLogResponse> listByType(EventType type){
        return eventLogRepository.findByTypeOrderByCreatedAtDesc(type).stream()
                .map(EventLogMapper::toResponse)
                .toList();
    }


    public List<EventLogResponse> listBetween(Long start, Long end){
        return eventLogRepository.findByCreatedAtBetweenOrderByCreatedAtDesc(start, end).stream()
                .map(EventLogMapper::toResponse)
                .toList();
    }


}
