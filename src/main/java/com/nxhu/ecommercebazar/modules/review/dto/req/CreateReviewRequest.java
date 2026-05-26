package com.nxhu.ecommercebazar.modules.review.dto.req;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateReviewRequest(
        @NotNull UUID productId,
        @NotNull UUID userId,
        @Min(1) @Max(5) @NotNull Integer rating,
        String comment
) {}
