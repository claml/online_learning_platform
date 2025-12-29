package com.example.onlinelearningplatform.controller;

import com.example.onlinelearningplatform.model.Role;
import com.example.onlinelearningplatform.repository.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/stats")
public class StatsController {
    private final EnrollmentRepository enrollmentRepository;
    private final SubmissionRepository submissionRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public StatsController(EnrollmentRepository enrollmentRepository, SubmissionRepository submissionRepository, CourseRepository courseRepository, UserRepository userRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.submissionRepository = submissionRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public Map<String, Object> overview() {
        Map<String, Object> map = new HashMap<>();
        map.put("totalEnrollments", enrollmentRepository.count());
        map.put("totalSubmissions", submissionRepository.count());
        map.put("courseCount", courseRepository.count());
        map.put("studentCount", userRepository.findAll().stream().filter(u -> u.getRole() == Role.STUDENT).count());
        map.put("instructorCount", userRepository.findAll().stream().filter(u -> u.getRole() == Role.INSTRUCTOR).count());
        return map;
    }
}
