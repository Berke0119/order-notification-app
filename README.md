# order-notification-app

Spring Boot ile geliştirilmiş **monolith** bir demo uygulama.  
Amaç: **RabbitMQ**, **Kafka** ve **Redis** teknolojilerini **aynı uygulama** içinde kullanarak öğrenmek ve pekiştirmek.

---

## ✨ Özellikler

- **Products**
  - Ürün oluşturma, listeleme, detay getirme
  - Ürün detayları **Redis Cache** ile saklanır (`@Cacheable`)

- **Orders**
  - Sipariş oluşturma (`POST /v1/orders`)
  - Sipariş durum güncelleme (`PATCH /v1/orders/{id}/status`)
  - Sipariş oluşturulduğunda:
    - **Kafka** → `order.events` topiğine `ORDER_CREATED` event
    - **RabbitMQ** → `notifications.exchange` exchange → `email.notifications` queue’ya sahte e-posta mesajı
  - Sipariş durumu değiştiğinde:
    - **Kafka** → `ORDER_STATUS_CHANGED` event
    - **RabbitMQ** → yine sahte e-posta kuyruğu

- **Event Logs**
  - Kafka Listener gelen event’leri **EventLog** tablosuna yazar
  - Event loglarını REST API üzerinden görebilirsin

- **Exception Handling**
  - Tüm hatalar standart JSON formatında döner (`ApiError`)
  - Alan bazlı validasyon hataları `fieldErrors` ile döner

---

## 🛠 Kullanılan Teknolojiler

- **Spring Boot 3.x**
  - Spring Web
  - Spring Data JPA (H2 veritabanı ile)
  - Validation (`jakarta.validation`)
  - Spring Cache + Spring Data Redis
  - Spring for Apache Kafka
  - Spring for RabbitMQ (AMQP)
- **H2 Database** (development için, memory mode)
- **Docker Compose** (Kafka, Zookeeper, RabbitMQ, Redis servisleri için)
- **Lombok**
- **Jackson** (JSON serileştirme)
- **Postman** (test için koleksiyon eklendi)
