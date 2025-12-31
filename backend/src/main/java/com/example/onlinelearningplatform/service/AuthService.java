package com.example.onlinelearningplatform.service;

import com.example.onlinelearningplatform.dto.AuthRequest;
import com.example.onlinelearningplatform.dto.AuthResponse;
import com.example.onlinelearningplatform.dto.MessageResponse;
import com.example.onlinelearningplatform.dto.RegisterRequest;
import com.example.onlinelearningplatform.model.Role;
import com.example.onlinelearningplatform.model.User;
import com.example.onlinelearningplatform.repository.UserRepository;
import com.example.onlinelearningplatform.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * Handles registration and login logic.
 */
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public MessageResponse register(RegisterRequest request) {
        userRepository.findByUsername(request.getUsername()).ifPresent(user -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "用户名已存在");
        });
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.STUDENT)
                .build();
        userRepository.save(user);
        return new MessageResponse("注册成功，请登录");
    }

    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "用户名或密码错误"));
        String token = jwtService.generateToken(user);
        return new AuthResponse(token, user.getUsername(), user.getRole().name());
    }
}
