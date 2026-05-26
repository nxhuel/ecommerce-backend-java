package com.nxhu.ecommercebazar.modules.order.persistence.repository;

import com.nxhu.ecommercebazar.modules.order.persistence.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByUserIdOrderByCreatedAtDesc(UUID userId);
    List<Order> findByStatusOrderByCreatedAtDesc(Order.OrderStatus status);
}
