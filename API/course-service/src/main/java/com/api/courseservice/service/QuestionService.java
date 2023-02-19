package com.api.courseservice.service;

import java.util.List;

import com.api.courseservice.DTO.QuestionForTest;

public interface QuestionService {
    List<QuestionForTest> getQuestionsByTestId(Long testId);
}
