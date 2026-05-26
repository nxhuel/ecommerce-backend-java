package com.nxhu.ecommercebazar.modules.order.dto.res;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderResponse(
        UUID id,
        UUID userId,
        String userName,
        String status,
        BigDecimal total,
        String paymentMethod,
        String shippingAddress,
        List<ItemResponse> items,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public record ItemResponse(
            UUID productId,
            String productName,
            String productImage,
            int quantity,
            BigDecimal price
    ) {}
}
