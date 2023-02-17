package com.api.courseservice.service;

import com.api.courseservice.model.TestResults;
import com.api.courseservice.repository.TestResultsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TestResultsServiceImpl implements TestResultsService {
    @Autowired
    TestResultsRepository testResultsRepository;

    @Override
    public Set<TestResults> findByUserIdEquals(Long userId){
        return testResultsRepository.findByUserIdEquals(userId);
    }
}
