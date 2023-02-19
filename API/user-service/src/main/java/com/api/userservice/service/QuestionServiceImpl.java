package com.api.userservice.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.userservice.DTO.QuestionDTO;
import com.api.userservice.model.Question;
import com.api.userservice.model.User;
import com.api.userservice.repository.QuestionRepository;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    private String pattern = "dd/MM/yyyy";

    private DateFormat df = new SimpleDateFormat(pattern);

    @Override
    public void addNewQuestion(User sender, String text) {
        Question question = new Question();
        question.setSender(sender);
        question.setSenderMessage(text);
        Date today = Calendar.getInstance().getTime();
        String date = df.format(today);
        question.setDateSenderMessage(date);
        questionRepository.save(question);
    }

    @Override
    public Question addAnswerByQuestionId(Long idQuestion, User recipient, String text) {
        Optional<Question> questionOptional = questionRepository.findById(idQuestion);
        if(questionOptional.isPresent()){
            Question question = questionOptional.get();
            question.setRecipientMessage(text);
            question.setRecipient(recipient);
            Date today = Calendar.getInstance().getTime();
            String date = df.format(today);
            question.setDateRecipientMessage(date);
            questionRepository.save(question);
            return question;
        }
        throw new EntityNotFoundException();
    }

    @Override
    public List<QuestionDTO> getCompletedQuestionsForHR(User user) {
        return questionRepository.getCompletedQuestionsForHR(user);
    }

    @Override
    public List<QuestionDTO> getUnfinishedQuestionsForHR(User user) {
        return questionRepository.getUnfinishedQuestionsForHR(user.getTeam().getId());
    }

    @Override
    public List<QuestionDTO> getCompletedQuestions(User user) {
        return questionRepository.getCompletedQuestions(user);
    }

    @Override
    public List<QuestionDTO> getUnfinishedQuestions(User user) {
        return questionRepository.getUnfinishedQuestions(user);
    }

}
