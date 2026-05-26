package com.nxhu.ecommercebazar.modules.notification.dto.res;

import java.time.LocalDateTime;
import java.util.UUID;

public record NotificationResponse(
        UUID id,
        UUID userId,
        String title,
        String message,
        Boolean read,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
