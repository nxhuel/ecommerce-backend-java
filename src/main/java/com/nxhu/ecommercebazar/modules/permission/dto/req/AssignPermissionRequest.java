package com.nxhu.ecommercebazar.modules.permission.dto.req;

import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.UUID;

public record AssignPermissionRequest(
        @NotBlank String key,
        List<UUID> roleIds
) {}
