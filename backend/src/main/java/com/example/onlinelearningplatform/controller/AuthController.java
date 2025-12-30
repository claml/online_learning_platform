package com.example.onlinelearningplatform.controller;

import com.example.onlinelearningplatform.dto.AuthRequest;
import com.example.onlinelearningplatform.dto.AuthResponse;
import com.example.onlinelearningplatform.dto.MessageResponse;
import com.example.onlinelearningplatform.dto.RegisterRequest;
import com.example.onlinelearningplatform.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> register(@RequestBody @Valid RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
