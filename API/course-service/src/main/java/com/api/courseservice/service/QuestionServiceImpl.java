package com.api.courseservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.courseservice.DTO.QuestionForTest;
import com.api.courseservice.repository.QuestionRepository;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public List<QuestionForTest> getQuestionsByTestId(Long testId) {
        return questionRepository.getListQuestionsByTestId(testId);
    }
}
