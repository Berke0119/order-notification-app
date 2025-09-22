package com.serinsoft.order_notification_app.repository;

import com.serinsoft.order_notification_app.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {

    boolean existsByName(String name);

    Optional<Product> findByName(String name);

}
