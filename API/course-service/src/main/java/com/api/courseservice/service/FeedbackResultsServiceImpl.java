package com.api.courseservice.service;

import com.api.courseservice.model.FeedbackResults;
import com.api.courseservice.repository.FeedbackResultsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class FeedbackResultsServiceImpl implements FeedbackResultsService {
    @Autowired
    FeedbackResultsRepository repository;

    @Override
    public Set<FeedbackResults> findByUserIdEquals(Long userId) {
        return repository.findByUserIdEquals(userId);
    }
}
