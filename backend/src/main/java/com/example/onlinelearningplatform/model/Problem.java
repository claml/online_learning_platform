package com.example.onlinelearningplatform.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(length = 4000)
    private String description;
    @Column(length = 1000)
    private String sampleInput;
    @Column(length = 1000)
    private String sampleOutput;
    private String tags;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}
