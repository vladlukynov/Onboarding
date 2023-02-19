package com.api.courseservice.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.api.courseservice.model.Feedback;
import com.api.courseservice.model.FeedbackResults;
@Repository
public interface FeedbackResultsRepository extends JpaRepository<FeedbackResults, Long> {
    @Query("select f from FeedbackResults f where f.userId = ?1")
    Set<FeedbackResults> findByUserIdEquals(@NonNull Long userId);

    @Query("SELECT COALESCE(count(t), 0) FROM FeedbackResults t " +
            "WHERE t.feedback IS NOT NULL " +
            "AND t.userId=:userId AND t.feedback.block.course.id=:courseId")
    Integer countFeedbackResultsByUserIdAndCourseId(Long userId, Long courseId);

    @Query("SELECT COALESCE(count(t.feedback.id), 0) FROM FeedbackResults t WHERE " +
            " t.feedback.block.course.id=:courseId AND t.feedback.block.course.id=:userId" +
            " group by t.feedback.id")
    Integer countFeedbackByCourseIdAndUserID(Long userId, Long courseId);

    @Query("SELECT t.feedback FROM FeedbackResults t " +
            "WHERE t.feedback.block.id=:blockId AND t.userId=:userId")
    List<Feedback> findFeedbacksByUserIdAndBlockId(Long userId, Long blockId);
}