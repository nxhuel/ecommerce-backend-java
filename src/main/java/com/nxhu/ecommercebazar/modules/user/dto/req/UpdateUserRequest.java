package com.nxhu.ecommercebazar.modules.user.dto.req;

public record UpdateUserRequest(
        String name,
        String avatar,
        String phone,
        String address
) {}
