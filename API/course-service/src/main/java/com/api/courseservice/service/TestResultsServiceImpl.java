package com.api.courseservice.service;

import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.courseservice.model.Test;
import com.api.courseservice.model.TestResults;
import com.api.courseservice.repository.TestRepository;
import com.api.courseservice.repository.TestResultsRepository;

@Service
public class TestResultsServiceImpl implements TestResultsService {
    @Autowired
    private TestResultsRepository testResultsRepository;

    @Autowired
    private TestRepository testRepository;

    @Override
    public Set<TestResults> findByUserIdEquals(Long userId) {
        return testResultsRepository.findByUserIdEquals(userId);
    }

    @Override
    public void setResultsForTest(Long testId, Long userId, Integer percent) {
        Optional<Test> testOptional = testRepository.findById(testId);
        if (testOptional.isPresent()) {
            TestResults testResults = new TestResults();
            testResults.setTest(testOptional.get());
            testResults.setPercents(percent);
            testResults.setUserId(userId);
            testResultsRepository.save(testResults);
        }
        throw new EntityNotFoundException();
    }
}
