package com.example.onlinelearningplatform.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "problem_id")
    private Problem problem;

    private String language;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String code;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String stdout;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String stderr;

    private String status;

    private LocalDateTime createdAt;
}
