package com.example.onlinelearningplatform.controller;

import com.example.onlinelearningplatform.dto.AuthRequest;
import com.example.onlinelearningplatform.dto.AuthResponse;
import com.example.onlinelearningplatform.dto.MessageResponse;
import com.example.onlinelearningplatform.dto.RegisterRequest;
import com.example.onlinelearningplatform.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import com.example.onlinelearningplatform.dto.RegisterRequest;
import com.example.onlinelearningplatform.model.Role;
import com.example.onlinelearningplatform.model.User;
import com.example.onlinelearningplatform.repository.UserRepository;
import com.example.onlinelearningplatform.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid RegisterRequest request) {
        if (userRepository.findByUsername(request.username()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        Role role = request.role() == null ? Role.STUDENT : request.role();
        User user = User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .role(role)
                .build();
        userRepository.save(user);
        String token = jwtService.generateToken(user);
        return ResponseEntity.ok(new AuthResponse(token, role.name()));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        var user = userRepository.findByUsername(request.username()).orElseThrow();
        return ResponseEntity.ok(new AuthResponse(jwtService.generateToken(user), user.getRole().name()));
    }
}
