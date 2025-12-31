package com.example.onlinelearningplatform.dto;

import com.example.onlinelearningplatform.model.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private Role role;
}
