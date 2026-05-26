package com.nxhu.ecommercebazar.modules.category.dto.req;

import jakarta.validation.constraints.NotBlank;

public record CreateCategoryRequest(
        @NotBlank String name,
        @NotBlank String slug,
        String icon,
        String description
) {}
