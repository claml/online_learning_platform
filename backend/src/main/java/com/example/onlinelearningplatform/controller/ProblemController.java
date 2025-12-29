package com.example.onlinelearningplatform.controller;

import com.example.onlinelearningplatform.model.Problem;
import com.example.onlinelearningplatform.model.Submission;
import com.example.onlinelearningplatform.repository.ProblemRepository;
import com.example.onlinelearningplatform.repository.SubmissionRepository;
import com.example.onlinelearningplatform.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/problems")
@CrossOrigin
public class ProblemController {
    private final ProblemRepository problemRepository;
    private final SubmissionRepository submissionRepository;
    private final UserRepository userRepository;

    public ProblemController(ProblemRepository problemRepository, SubmissionRepository submissionRepository, UserRepository userRepository) {
        this.problemRepository = problemRepository;
        this.submissionRepository = submissionRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<Problem> list(@RequestParam(required = false) Long courseId) {
        if (courseId != null) {
            return problemRepository.findByCourseId(courseId);
        }
        return problemRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Problem> detail(@PathVariable Long id) {
        return problemRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR','ADMIN')")
    @PostMapping
    public Problem create(@RequestBody @Valid Problem problem) {
        return problemRepository.save(problem);
    }

    @PostMapping("/{id}/submit")
    public ResponseEntity<Submission> submit(@PathVariable Long id, @RequestBody Submission submission, Authentication authentication) {
        var user = userRepository.findByUsername(authentication.getName()).orElseThrow();
        var problem = problemRepository.findById(id).orElseThrow();
        submission.setProblem(problem);
        submission.setUser(user);
        submission.setCreatedAt(LocalDateTime.now());
        submission.setStatus("SAVED");
        return ResponseEntity.ok(submissionRepository.save(submission));
    }

    @GetMapping("/{id}/submissions")
    public List<Submission> submissions(@PathVariable Long id) {
        var problem = problemRepository.findById(id).orElseThrow();
        return submissionRepository.findByProblemOrderByCreatedAtDesc(problem);
    }
}
