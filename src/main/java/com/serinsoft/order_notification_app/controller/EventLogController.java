package com.serinsoft.order_notification_app.controller;

import com.serinsoft.order_notification_app.dto.EventLogResponse;
import com.serinsoft.order_notification_app.entity.EventType;
import com.serinsoft.order_notification_app.service.EventLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/events")
@Validated
public class EventLogController {


    private final EventLogService eventLogService;

    public EventLogController(EventLogService eventLogService) {
        this.eventLogService = eventLogService;
    }

    @GetMapping
    public ResponseEntity<List<EventLogResponse>> listAllDesc() {
        return ResponseEntity.ok(eventLogService.listAllDesc());
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<EventLogResponse>> listByType(@PathVariable EventType type) {
        return ResponseEntity.ok(eventLogService.listByType(type));
    }

    /**
     * Örnek:
     * GET /v1/events/between?start=2025-09-01T00:00:00Z&end=2025-09-22T00:00:00Z
     */
    @GetMapping("/between")
    public ResponseEntity<List<EventLogResponse>> listBetween(
            @RequestParam("start") Long start,
            @RequestParam("end") Long end
    ) {
        return ResponseEntity.ok(eventLogService.listBetween(start, end));
    }

}
