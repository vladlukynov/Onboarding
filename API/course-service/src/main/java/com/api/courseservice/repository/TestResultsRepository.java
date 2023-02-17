package com.api.courseservice.repository;

import com.api.courseservice.model.TestResults;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface TestResultsRepository extends JpaRepository<TestResults, Long> {
    @Query("select t from TestResults t where t.userId = ?1")
    Set<TestResults> findByUserIdEquals(Long userId);
}