package com.api.courseservice.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import com.api.courseservice.model.FeedbackResults;

public interface FeedbackResultsRepository extends JpaRepository<FeedbackResults, Long> {
    @Query("select f from FeedbackResults f where f.userId = ?1")
    Set<FeedbackResults> findByUserIdEquals(@NonNull Long userId);

    @Query("SELECT COALESCE(count(t), 0) FROM FeedbackResults t WHERE t.feedback IS NOT NULL AND t.userId=:userId AND t.feedback.course.id=:courseId")
    Integer countFeedbackResultsByUserIdAndCourseId(Long userId, Long courseId);

    @Query("SELECT COALESCE(count(t.feedback.id), 0) FROM FeedbackResults t WHERE t.feedback IS NOT NULL AND t.userId=:userId" +
            " AND t.feedback.course IS NOT NULL AND t.feedback.course.id=:courseId group by t.feedback.id")
    Integer countFeedbackByCourseIdAndUserID(Long userId, Long courseId);
}