package com.nxhu.ecommercebazar.modules.notification.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateNotificationRequest(
        @NotNull UUID userId,
        @NotBlank String title,
        @NotBlank String message
) {}
