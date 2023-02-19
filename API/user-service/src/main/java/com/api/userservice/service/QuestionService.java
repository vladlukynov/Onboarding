package com.api.userservice.service;

import java.util.List;

import com.api.userservice.DTO.QuestionDTO;
import com.api.userservice.model.Question;
import com.api.userservice.model.User;

public interface QuestionService {
    void addNewQuestion(User sender, String text);
    Question addAnswerByQuestionId(Long idQuestion, User recipient, String text);
    List<QuestionDTO> getCompletedQuestionsForHR(User user);
    List<QuestionDTO> getUnfinishedQuestionsForHR(User user);
    List<QuestionDTO> getCompletedQuestions(User user);
    List<QuestionDTO> getUnfinishedQuestions(User user);
}
