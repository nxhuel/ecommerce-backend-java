package com.nxhu.ecommercebazar.modules.role.dto.res;

import java.time.LocalDateTime;
import java.util.UUID;

public record RoleResponse(
        UUID id,
        String name,
        String label,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
