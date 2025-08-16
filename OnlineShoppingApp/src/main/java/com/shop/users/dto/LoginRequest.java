package com.shop.users.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(@NotBlank(message = "username must required")
													String username,
													@NotBlank(message = "password must required")
													String password) {

}
