package com.nxhu.ecommercebazar.modules.category.dto.req;

public record UpdateCategoryRequest(
        String name,
        String slug,
        String icon,
        String description
) {}
