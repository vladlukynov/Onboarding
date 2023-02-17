package com.api.courseservice.model;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "testresults")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerVars {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    private String variant;
}
