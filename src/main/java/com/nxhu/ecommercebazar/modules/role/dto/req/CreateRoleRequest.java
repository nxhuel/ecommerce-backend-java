package com.nxhu.ecommercebazar.modules.role.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateRoleRequest(
        @NotBlank String name,
        @NotBlank String label
) {}
