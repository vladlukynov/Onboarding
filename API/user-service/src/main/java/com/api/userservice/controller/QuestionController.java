package com.api.userservice.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.userservice.model.Question;
import com.api.userservice.model.User;
import com.api.userservice.security.jwt.JwtService;
import com.api.userservice.service.NotificationService;
import com.api.userservice.service.QuestionService;
import com.api.userservice.service.UserService;
import com.api.userservice.service.UserTokenService;
import com.api.userservice.utils.AppError;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserTokenService userTokenService;

    @Autowired
    private NotificationService notificationService;

    @RequestMapping(path = "/add-new-question", method = RequestMethod.GET)
    public ResponseEntity<?> addNewQuestion(@RequestParam String text, HttpServletRequest request) {
        ResponseEntity<?> responseEntity = getUserByRequest(request);
        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return responseEntity;
        }
        User user = (User) responseEntity.getBody();
        questionService.addNewQuestion(user, text);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "/add-answer", method = RequestMethod.GET)
    public ResponseEntity<?> addAnswer(@RequestParam Long idQuestion,
                                       @RequestParam String text,
                                       HttpServletRequest request) {
        ResponseEntity<?> responseEntity = getUserByRequest(request);
        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return responseEntity;
        }
        User user = (User) responseEntity.getBody();
        try {
            Question question = questionService.addAnswerByQuestionId(idQuestion, user, text);
            notificationService.addNewNotification("Вам ответили на обращение \""
                    + question.getSenderMessage() + "\". Посморите в разделе обращений.", question.getSender());
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(
                    new AppError(HttpStatus.NOT_FOUND.value(),
                            "This question doesn't exist"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "/get-questions", method = RequestMethod.GET)
    public ResponseEntity<?> getQuestions(@RequestParam boolean isCompleted, HttpServletRequest request) {
        ResponseEntity<?> responseEntity = getUserByRequest(request);
        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return responseEntity;
        }
        User user = (User) responseEntity.getBody();
        if (user.getId() == 1) {
            if (isCompleted) {
                return new ResponseEntity<>(questionService.getCompletedQuestionsForHR(user), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(questionService.getUnfinishedQuestionsForHR(user), HttpStatus.OK);
            }
        } else {
            if (isCompleted) {
                return new ResponseEntity<>(questionService.getCompletedQuestions(user), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(questionService.getUnfinishedQuestions(user), HttpStatus.OK);
            }
        }
    }

    private ResponseEntity<?> getUserByRequest(HttpServletRequest request) {
        String email;
        String token = resolveToken(request);
        try {
            email = jwtService.extractUserInfo(token);
        } catch (URISyntaxException | IOException e) {
            return new ResponseEntity<>(
                    new AppError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Extracting user info by token is failed."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        User user;
        try {
            user = userService.findUserByEmail(email);
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(
                    new AppError(HttpStatus.NOT_FOUND.value(),
                            "User with email " + email + " does not exist."), HttpStatus.NOT_FOUND);
        }
        if (!userTokenService.isCorrectAccessToken(user, token)) {
            return new ResponseEntity<>(
                    new AppError(HttpStatus.UNAUTHORIZED.value(),
                            "This access token does not exist."), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
