package com.nxhu.ecommercebazar.modules.order.dto.req;

import jakarta.validation.constraints.NotBlank;

public record UpdateOrderStatusRequest(
        @NotBlank String status
) {}
