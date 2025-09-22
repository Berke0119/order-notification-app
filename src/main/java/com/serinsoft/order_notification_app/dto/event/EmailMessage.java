package com.serinsoft.order_notification_app.dto.event;

public record EmailMessage(
        String to,
        String subject,
        String body
) {
}
