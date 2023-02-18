package com.api.courseservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.api.courseservice.model.Feedback;
import com.api.courseservice.model.Test;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    @Query("SELECT COALESCE(count(t.questions.size), 0) FROM Feedback t WHERE t.course.id=:courseId")
    Integer getCountQuestionsByCourse(Long courseId);

    @Query("SELECT COALESCE(count(t), 0) FROM Feedback t WHERE t.course.id=:courseId")
    Integer getCountFeedbackByCourse(Long courseId);
}