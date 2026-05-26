package com.nxhu.ecommercebazar.modules.setting.dto.req;

public record UpdateSettingsRequest(
        String storeName,
        String currency,
        String city,
        String country,
        String email,
        String phone,
        String address
) {}
