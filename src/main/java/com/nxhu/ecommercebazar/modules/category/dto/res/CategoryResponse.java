package com.nxhu.ecommercebazar.modules.category.dto.res;

import java.time.LocalDateTime;
import java.util.UUID;

public record CategoryResponse(
        UUID id,
        String name,
        String slug,
        String icon,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
