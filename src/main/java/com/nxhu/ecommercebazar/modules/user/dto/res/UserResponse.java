package com.nxhu.ecommercebazar.modules.user.dto.res;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String name,
        String email,
        String role,
        String avatar,
        String phone,
        String address,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
