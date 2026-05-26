package com.nxhu.ecommercebazar.modules.favorite.dto.res;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record FavoriteResponse(
        UUID id,
        UUID userId,
        UUID productId,
        String productName,
        BigDecimal productPrice,
        String productImage,
        LocalDateTime createdAt
) {}
