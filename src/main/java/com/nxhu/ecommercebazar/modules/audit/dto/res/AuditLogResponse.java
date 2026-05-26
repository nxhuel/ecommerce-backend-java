package com.nxhu.ecommercebazar.modules.audit.dto.res;

import java.time.LocalDateTime;
import java.util.UUID;

public record AuditLogResponse(
        UUID id,
        UUID userId,
        String userName,
        String action,
        String entity,
        LocalDateTime createdAt
) {}
