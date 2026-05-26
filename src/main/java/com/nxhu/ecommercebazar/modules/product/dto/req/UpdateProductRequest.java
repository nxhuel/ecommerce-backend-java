package com.nxhu.ecommercebazar.modules.product.dto.req;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record UpdateProductRequest(
        String name,
        String slug,
        UUID categoryId,
        BigDecimal price,
        UUID discountId,
        Integer stock,
        String brand,
        String sku,
        String description,
        Boolean featured,
        List<String> images,
        List<String> tags
) {}
