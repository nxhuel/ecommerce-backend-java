package com.nxhu.ecommercebazar.modules.user.dto.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest(
        @NotBlank String name,
        @NotBlank @Email String email,
        @NotBlank String password,
        @NotBlank String roleName,
        String avatar,
        String phone,
        String address
) {}
