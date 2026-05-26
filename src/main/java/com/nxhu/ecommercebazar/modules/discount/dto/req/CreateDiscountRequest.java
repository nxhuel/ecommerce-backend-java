package com.nxhu.ecommercebazar.modules.discount.dto.req;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreateDiscountRequest(
        @NotBlank String code,
        @Min(0) @Max(100) @NotNull Integer percentage,
        @NotNull Boolean active,
        @NotNull LocalDateTime validUntil,
        String description
) {}
