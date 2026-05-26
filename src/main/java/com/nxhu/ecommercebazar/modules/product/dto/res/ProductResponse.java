package com.nxhu.ecommercebazar.modules.product.dto.res;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ProductResponse(
        UUID id,
        String name,
        String slug,
        UUID categoryId,
        String categoryName,
        BigDecimal price,
        UUID discountId,
        String discountCode,
        Integer stock,
        Double rating,
        Integer reviewCount,
        String brand,
        String sku,
        String description,
        Boolean featured,
        List<String> images,
        List<String> tags,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
