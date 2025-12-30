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
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    @Column(length = 4000)
    private String content;
    private String videoUrl;
    private Integer lessonOrder;
    private String exampleCode;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}
