package com.api.courseservice.repository;

import com.api.courseservice.model.FeedbackResults;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.Set;

public interface FeedbackResultsRepository extends JpaRepository<FeedbackResults, Long> {
    @Query("select f from FeedbackResults f where f.userId = ?1")
    Set<FeedbackResults> findByUserIdEquals(@NonNull Long userId);
}