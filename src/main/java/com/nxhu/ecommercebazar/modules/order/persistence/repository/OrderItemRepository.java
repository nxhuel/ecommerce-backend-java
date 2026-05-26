package com.nxhu.ecommercebazar.modules.order.persistence.repository;

import com.nxhu.ecommercebazar.modules.order.persistence.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {
    List<OrderItem> findByOrderId(UUID orderId);
}
