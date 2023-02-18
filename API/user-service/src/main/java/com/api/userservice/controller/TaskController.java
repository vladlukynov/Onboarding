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
import org.springframework.web.bind.annotation.RestController;

import com.api.userservice.model.User;
import com.api.userservice.security.jwt.JwtService;
import com.api.userservice.service.NotificationService;
import com.api.userservice.service.TaskService;
import com.api.userservice.service.UserActivityService;
import com.api.userservice.service.UserService;
import com.api.userservice.service.UserTokenService;
import com.api.userservice.utils.AppError;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserTokenService userTokenService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserActivityService userActivityService;

    @RequestMapping(path = "/get-task-filter", method = RequestMethod.GET)
    public ResponseEntity<?> getTaskFilter(boolean completed, HttpServletRequest request) {
        ResponseEntity<?> responseEntity = getUserByRequest(request);
        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return responseEntity;
        }
        User user = (User) responseEntity.getBody();
        return new ResponseEntity<>(taskService.getTasksByUserId(user.getId(), completed), HttpStatus.OK);
    }

    @RequestMapping(path = "/get-tasks-by-user-id", method = RequestMethod.GET)
    public ResponseEntity<?> getTasksByUserId(Long userId, HttpServletRequest request) {
        ResponseEntity<?> responseEntity = getUserByRequest(request);
        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return responseEntity;
        }
        User user = (User) responseEntity.getBody();
        if ((user == null) || (user.getPost() == null) || (user.getPost().getId() != 1)) {
            return new ResponseEntity<>(
                    new AppError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Not enough rights to execute"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        User worker;
        try {
            worker = userService.getUserById(userId);
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(
                    new AppError(HttpStatus.NOT_FOUND.value(),
                            "Worker with id " + user + " doesn't exist"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(taskService.getTasksByUserId(userId), HttpStatus.OK);
    }

    @RequestMapping(path = "/set-competed-task", method = RequestMethod.GET)
    public ResponseEntity<?> setCompletedTask(Long taskId, Boolean completed, HttpServletRequest request) {
        ResponseEntity<?> responseEntity = getUserByRequest(request);
        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return responseEntity;
        }
        User user = (User) responseEntity.getBody();
        boolean check = taskService.setCompletedTask(user.getId(), taskId, completed);
        if (check) {
            if (taskService.getTaskById(taskId).isCompleted()) {
                userActivityService.addNewUserActivity("Выполнение задачи \"" + taskService.getTaskById(taskId).getHeader() + "\"", user, null);
            } else {
                userActivityService.addNewUserActivity("Отмена выполнения задачи \"" + taskService.getTaskById(taskId).getHeader() + "\"", user, null);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    new AppError(HttpStatus.CONFLICT.value(),
                            "Task with id " + taskId + " doesn't belong this user"), HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(path = "/add-new-task", method = RequestMethod.GET)
    public ResponseEntity<?> addNewTask(Long userId, String header,
                                        String description, String deadline, HttpServletRequest request) {
        ResponseEntity<?> responseEntity = getUserByRequest(request);
        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return responseEntity;
        }
        User user = (User) responseEntity.getBody();
        if ((user == null) || (user.getPost() == null) || (user.getPost().getId() != 1)) {
            return new ResponseEntity<>(
                    new AppError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Not enough rights to execute"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        User worker;
        try {
            worker = userService.getUserById(userId);
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(
                    new AppError(HttpStatus.NOT_FOUND.value(),
                            "Worker with id " + user + " doesn't exist"), HttpStatus.NOT_FOUND);
        }
        String content = "Вам назначена новая задача. Скорее посмотрите её в списке задач!";
        notificationService.addNewNotification(content, worker);
        taskService.addNewTask(worker, header, description, deadline);
        return new ResponseEntity<>(HttpStatus.OK);
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