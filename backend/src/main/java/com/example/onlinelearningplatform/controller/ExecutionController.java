package com.example.onlinelearningplatform.controller;

import com.example.onlinelearningplatform.dto.ExecutionRequest;
import com.example.onlinelearningplatform.dto.ExecutionResponse;
import com.example.onlinelearningplatform.service.ExecutionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/execute")
@CrossOrigin
public class ExecutionController {

    private final ExecutionService executionService;

    public ExecutionController(ExecutionService executionService) {
        this.executionService = executionService;
    }

    @PostMapping
    @PreAuthorize("hasRole('STUDENT') or hasRole('INSTRUCTOR') or hasRole('ADMIN')")
    public ResponseEntity<ExecutionResponse> execute(@RequestBody @Valid ExecutionRequest request) throws Exception {
        return ResponseEntity.ok(executionService.execute(request));
    }
}
