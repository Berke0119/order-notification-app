package com.serinsoft.order_notification_app.dto;

import com.serinsoft.order_notification_app.entity.EventType;

public record EventLogResponse(
        Long id,
        EventType type,
        String payload,
        Long createdAt
) {
}
