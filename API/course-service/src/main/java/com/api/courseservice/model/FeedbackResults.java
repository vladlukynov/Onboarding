package com.api.courseservice.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "feedbackresults")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackResults {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "feedback_id")
    private Feedback feedback;

    private String userAnswer;

    private Long userId;
}
