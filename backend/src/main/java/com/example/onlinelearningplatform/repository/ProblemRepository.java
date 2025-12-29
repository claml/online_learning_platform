package com.example.onlinelearningplatform.repository;

import com.example.onlinelearningplatform.model.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
    List<Problem> findByCourseId(Long courseId);
}
