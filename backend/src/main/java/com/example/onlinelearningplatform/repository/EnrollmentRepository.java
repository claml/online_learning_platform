package com.example.onlinelearningplatform.repository;

import com.example.onlinelearningplatform.model.Course;
import com.example.onlinelearningplatform.model.Enrollment;
import com.example.onlinelearningplatform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    Optional<Enrollment> findByUserAndCourse(User user, Course course);
    List<Enrollment> findByUser(User user);
}
