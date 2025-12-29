package com.example.onlinelearningplatform.dto;

import jakarta.validation.constraints.NotBlank;

public record ExecutionRequest(@NotBlank String language, @NotBlank String code, String stdin) {
}
