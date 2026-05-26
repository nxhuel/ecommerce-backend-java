package com.nxhu.ecommercebazar.modules.discount.dto.req;

import java.time.LocalDateTime;

public record UpdateDiscountRequest(
        String code,
        Integer percentage,
        Boolean active,
        LocalDateTime validUntil,
        String description
) {}
