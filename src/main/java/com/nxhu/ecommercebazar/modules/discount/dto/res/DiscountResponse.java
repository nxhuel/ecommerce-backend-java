package com.nxhu.ecommercebazar.modules.discount.dto.res;

import java.time.LocalDateTime;
import java.util.UUID;

public record DiscountResponse(
        UUID id,
        String code,
        Integer percentage,
        Boolean active,
        LocalDateTime validUntil,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
