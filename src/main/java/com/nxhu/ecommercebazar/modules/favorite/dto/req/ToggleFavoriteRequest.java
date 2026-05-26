package com.nxhu.ecommercebazar.modules.favorite.dto.req;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ToggleFavoriteRequest(
        @NotNull UUID userId,
        @NotNull UUID productId
) {}
