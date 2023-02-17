package com.api.courseservice.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "feedback")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "feedback", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Question> questions;

    @OneToMany(mappedBy = "feedback", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<FeedbackResults> results;

    private Integer numberInCourse;
}
