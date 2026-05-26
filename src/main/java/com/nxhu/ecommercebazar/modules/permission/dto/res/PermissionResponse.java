package com.nxhu.ecommercebazar.modules.permission.dto.res;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record PermissionResponse(
        UUID id,
        String key,
        List<String> roleNames,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
