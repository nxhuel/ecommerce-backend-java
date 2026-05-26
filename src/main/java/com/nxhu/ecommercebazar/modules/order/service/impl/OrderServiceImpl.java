package com.nxhu.ecommercebazar.modules.order.service.impl;

import com.nxhu.ecommercebazar.modules.order.dto.req.CreateOrderRequest;
import com.nxhu.ecommercebazar.modules.order.dto.req.UpdateOrderStatusRequest;
import com.nxhu.ecommercebazar.modules.order.dto.res.OrderResponse;
import com.nxhu.ecommercebazar.modules.order.persistence.entity.Order;
import com.nxhu.ecommercebazar.modules.order.persistence.entity.OrderItem;
import com.nxhu.ecommercebazar.modules.order.persistence.repository.OrderItemRepository;
import com.nxhu.ecommercebazar.modules.order.persistence.repository.OrderRepository;
import com.nxhu.ecommercebazar.modules.order.service.OrderService;
import com.nxhu.ecommercebazar.modules.product.persistence.entity.Product;
import com.nxhu.ecommercebazar.modules.product.persistence.repository.ProductRepository;
import com.nxhu.ecommercebazar.modules.product.persistence.repository.ProductImageRepository;
import com.nxhu.ecommercebazar.modules.user.persistence.entity.User;
import com.nxhu.ecommercebazar.modules.user.persistence.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> findAll() {
        return orderRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse findById(UUID id) {
        return toResponse(findOrder(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> findByUserId(UUID userId) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public OrderResponse create(CreateOrderRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + request.userId()));

        BigDecimal total = BigDecimal.ZERO;
        List<OrderItem> items = new ArrayList<>();

        for (CreateOrderRequest.Item item : request.items()) {
            Product product = productRepository.findById(item.productId())
                    .orElseThrow(() -> new EntityNotFoundException("Product not found: " + item.productId()));
            if (product.getStock() < item.quantity()) {
                throw new IllegalArgumentException("Insufficient stock for product: " + product.getName());
            }
            product.setStock(product.getStock() - item.quantity());
            productRepository.save(product);

            BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(item.quantity()));
            total = total.add(subtotal);

            items.add(OrderItem.builder()
                    .product(product)
                    .quantity(item.quantity())
                    .price(product.getPrice())
                    .build());
        }

        Order order = Order.builder()
                .user(user)
                .status(Order.OrderStatus.PENDING)
                .total(total)
                .paymentMethod(request.paymentMethod())
                .shippingAddress(request.shippingAddress())
                .build();
        order = orderRepository.save(order);

        Order finalOrder = order;
        for (OrderItem item : items) {
            item.setOrder(finalOrder);
            orderItemRepository.save(item);
        }
        return toResponse(orderRepository.findById(order.getId()).orElseThrow());
    }

    @Override
    @Transactional
    public OrderResponse updateStatus(UUID id, UpdateOrderStatusRequest request) {
        Order order = findOrder(id);
        order.setStatus(Order.OrderStatus.valueOf(request.status().toUpperCase()));
        return toResponse(orderRepository.save(order));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Order order = findOrder(id);
        orderRepository.delete(order);
    }

    private Order findOrder(UUID id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found: " + id));
    }

    private OrderResponse toResponse(Order order) {
        List<OrderResponse.ItemResponse> items = order.getItems().stream()
                .map(item -> {
                    String image = productImageRepository.findByProductIdOrderBySortOrder(item.getProduct().getId())
                            .stream().findFirst().map(img -> img.getImageUrl()).orElse(null);
                    return new OrderResponse.ItemResponse(
                            item.getProduct().getId(),
                            item.getProduct().getName(),
                            image,
                            item.getQuantity(),
                            item.getPrice()
                    );
                })
                .toList();
        return new OrderResponse(
                order.getId(),
                order.getUser().getId(),
                order.getUser().getName(),
                order.getStatus().name(),
                order.getTotal(),
                order.getPaymentMethod(),
                order.getShippingAddress(),
                items,
                order.getCreatedAt(),
                order.getUpdatedAt()
        );
    }
}
