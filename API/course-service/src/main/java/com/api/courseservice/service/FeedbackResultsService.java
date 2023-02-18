package com.api.courseservice.service;

import java.util.Set;

import org.springframework.lang.NonNull;

import com.api.courseservice.model.FeedbackResults;

public interface FeedbackResultsService {
    Set<FeedbackResults> findByUserIdEquals(@NonNull Long userId);
}
