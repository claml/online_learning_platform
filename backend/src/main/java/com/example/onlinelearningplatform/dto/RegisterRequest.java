package com.example.onlinelearningplatform.dto;

import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(@NotBlank String username, @NotBlank String password) {
}
