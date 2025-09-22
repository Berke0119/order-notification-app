package com.serinsoft.order_notification_app.mapper;

import com.serinsoft.order_notification_app.dto.EventLogResponse;
import com.serinsoft.order_notification_app.entity.EventLog;

public final class EventLogMapper {

    private EventLogMapper() {
    }

    public static EventLogResponse toResponse(EventLog eventLog){
        return new EventLogResponse(eventLog.getId(), eventLog.getType(), eventLog.getPayload(), eventLog.getCreatedAt());
    }

}
