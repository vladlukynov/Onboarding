package com.api.courseservice.repository;

import com.api.courseservice.model.TestResults;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface TestResultsRepository extends JpaRepository<TestResults, Long> {
    @Query("select t from TestResults t where t.userId=:userId")
    Set<TestResults> findByUserIdEquals(Long userId);

    @Query("SELECT t FROM TestResults t WHERE t.userId=:userId AND t.test.course.id=:courseId")
    Set<TestResults> findByUserIdAndCourseId(Long userId, Long courseId);

    @Query("SELECT COALESCE(count(t), 0) FROM TestResults t WHERE t.userId=:userId AND t.test.course.id=:courseId")
    Integer countTestResultsByUserIdAndCourseId(Long userId, Long courseId);
}