package com.nxhu.ecommercebazar.modules.product.dto.req;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CreateProductRequest(
        @NotBlank String name,
        @NotBlank String slug,
        @NotNull UUID categoryId,
        @NotNull BigDecimal price,
        UUID discountId,
        @Min(0) @NotNull Integer stock,
        @NotBlank String brand,
        @NotBlank String sku,
        String description,
        @NotNull Boolean featured,
        List<String> images,
        List<String> tags
) {}
