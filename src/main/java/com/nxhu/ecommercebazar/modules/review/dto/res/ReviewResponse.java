package com.nxhu.ecommercebazar.modules.review.dto.res;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReviewResponse(
        UUID id,
        UUID productId,
        String productName,
        UUID userId,
        String userName,
        Integer rating,
        String comment,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
