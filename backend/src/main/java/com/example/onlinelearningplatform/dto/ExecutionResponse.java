package com.example.onlinelearningplatform.dto;

public record ExecutionResponse(String stdout, String stderr, int exitCode, long timeMs, boolean truncated) {
}
