package com.api.courseservice.service;

import com.api.courseservice.model.FeedbackResults;
import org.springframework.lang.NonNull;

import java.util.Set;

public interface FeedbackResultsService {
    Set<FeedbackResults> findByUserIdEquals(@NonNull Long userId);
}
