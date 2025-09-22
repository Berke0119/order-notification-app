package com.serinsoft.order_notification_app.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(
        name = "event_logs",
        indexes = {
                @Index(name = "ix_event_type_created_at", columnList = "type, createdAt"),
        }


)
public class EventLog implements Serializable {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private EventType type;

        @Lob
        @Column(nullable = false)
        private String payload;

        @Column(nullable = false)
        private Long createdAt;

        public EventLog() {
        }

        public EventLog(EventType type, String payload, Long createdAt) {
                this.type = type;
                this.payload = payload;
                this.createdAt = createdAt;
        }

        public EventLog(Long id, EventType type, String payload, Long createdAt) {
                this.id = id;
                this.type = type;
                this.payload = payload;
                this.createdAt = createdAt;
        }

        public Long getId() {

                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public EventType getType() {
                return type;
        }

        public void setType(EventType type) {
                this.type = type;
        }

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                EventLog eventLog = (EventLog) o;
                return Objects.equals(id, eventLog.id) && type == eventLog.type && Objects.equals(payload, eventLog.payload) && Objects.equals(createdAt, eventLog.createdAt);
        }

        @Override
        public int hashCode() {
                return Objects.hash(id, type, payload, createdAt);
        }

        public String getPayload() {
                return payload;
        }

        public void setPayload(String payload) {
                this.payload = payload;
        }

        public Long getCreatedAt() {
                return createdAt;
        }

        public void setCreatedAt(Long createdAt) {
                this.createdAt = createdAt;
        }
}
