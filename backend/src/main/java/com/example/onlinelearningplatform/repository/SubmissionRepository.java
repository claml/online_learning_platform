package com.example.onlinelearningplatform.repository;

import com.example.onlinelearningplatform.model.Problem;
import com.example.onlinelearningplatform.model.Submission;
import com.example.onlinelearningplatform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findTop10ByUserOrderByCreatedAtDesc(User user);
    List<Submission> findByProblemOrderByCreatedAtDesc(Problem problem);
}
