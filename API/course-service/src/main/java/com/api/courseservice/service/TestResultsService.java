package com.api.courseservice.service;

import java.util.Set;

import com.api.courseservice.model.TestResults;

public interface TestResultsService {
    Set<TestResults> findByUserIdEquals(Long userId);
    void setResultsForTest(Long testId, Long userId, Integer percent);
}
