package com.api.courseservice.service;

import com.api.courseservice.model.TestResults;

import java.util.Set;

public interface TestResultsService {
    Set<TestResults> findByUserIdEquals(Long userId);
}
