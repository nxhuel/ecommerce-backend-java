package com.nxhu.ecommercebazar.modules.order.service;

import com.nxhu.ecommercebazar.modules.order.dto.req.CreateOrderRequest;
import com.nxhu.ecommercebazar.modules.order.dto.req.UpdateOrderStatusRequest;
import com.nxhu.ecommercebazar.modules.order.dto.res.OrderResponse;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    List<OrderResponse> findAll();
    OrderResponse findById(UUID id);
    List<OrderResponse> findByUserId(UUID userId);
    OrderResponse create(CreateOrderRequest request);
    OrderResponse updateStatus(UUID id, UpdateOrderStatusRequest request);
    void delete(UUID id);
}
