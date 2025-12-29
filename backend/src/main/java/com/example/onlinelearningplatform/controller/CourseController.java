package com.example.onlinelearningplatform.controller;

import com.example.onlinelearningplatform.model.Course;
import com.example.onlinelearningplatform.model.Enrollment;
import com.example.onlinelearningplatform.model.User;
import com.example.onlinelearningplatform.repository.CourseRepository;
import com.example.onlinelearningplatform.repository.EnrollmentRepository;
import com.example.onlinelearningplatform.repository.LessonRepository;
import com.example.onlinelearningplatform.repository.UserRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin
public class CourseController {

    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;

    public CourseController(CourseRepository courseRepository, LessonRepository lessonRepository, EnrollmentRepository enrollmentRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.lessonRepository = lessonRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public Page<Course> list(@RequestParam(defaultValue = "") String keyword,
                             @RequestParam(required = false) String language,
                             @RequestParam(required = false) String domain,
                             @RequestParam(required = false) String difficulty,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "12") int size) {
        Specification<Course> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (!keyword.isBlank()) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("title")), "%" + keyword.toLowerCase() + "%"));
            }
            if (language != null && !language.isBlank()) {
                predicate = cb.and(predicate, cb.equal(root.get("language"), language));
            }
            if (domain != null && !domain.isBlank()) {
                predicate = cb.and(predicate, cb.equal(root.get("domain"), domain));
            }
            if (difficulty != null && !difficulty.isBlank()) {
                predicate = cb.and(predicate, cb.equal(root.get("difficulty"), difficulty));
            }
            return predicate;
        };
        return courseRepository.findAll(spec, PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> detail(@PathVariable Long id) {
        return courseRepository.findById(id)
                .map(course -> ResponseEntity.ok(course))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/lessons")
    public List<?> lessons(@PathVariable Long id) {
        return lessonRepository.findByCourseIdOrderByLessonOrder(id);
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR','ADMIN')")
    @PostMapping
    public Course create(@RequestBody Course course) {
        return courseRepository.save(course);
    }

    @PostMapping("/{id}/enroll")
    public ResponseEntity<Enrollment> enroll(@PathVariable Long id, Authentication authentication) {
        var user = userRepository.findByUsername(authentication.getName()).orElseThrow();
        var course = courseRepository.findById(id).orElseThrow();
        var existing = enrollmentRepository.findByUserAndCourse(user, course);
        if (existing.isPresent()) {
            return ResponseEntity.ok(existing.get());
        }
        Enrollment enrollment = Enrollment.builder()
                .course(course)
                .user(user)
                .joinedAt(LocalDateTime.now())
                .progress(0)
                .completedLessons(0)
                .build();
        return ResponseEntity.ok(enrollmentRepository.save(enrollment));
    }
}
