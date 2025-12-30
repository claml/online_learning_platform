package com.example.onlinelearningplatform.dto;

public record AuthResponse(String token, String username, String role) {
public record AuthResponse(String token, String role) {
}
