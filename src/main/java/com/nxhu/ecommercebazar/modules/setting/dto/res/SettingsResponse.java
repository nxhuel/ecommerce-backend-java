package com.nxhu.ecommercebazar.modules.setting.dto.res;

import java.time.LocalDateTime;
import java.util.UUID;

public record SettingsResponse(
        UUID id,
        String storeName,
        String currency,
        String city,
        String country,
        String email,
        String phone,
        String address,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
