package com.nxhu.ecommercebazar.modules.permission.dto.req;

import jakarta.validation.constraints.NotBlank;

public record CreatePermissionRequest(
        @NotBlank String key
) {}
