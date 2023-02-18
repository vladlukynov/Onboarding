package com.api.userservice.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.api.userservice.DTO.NotificationDTO;
import com.api.userservice.model.User;
import com.api.userservice.security.jwt.JwtService;
import com.api.userservice.service.NotificationService;
import com.api.userservice.service.TaskService;
import com.api.userservice.service.UserActivityService;
import com.api.userservice.service.UserService;
import com.api.userservice.service.UserTokenService;
import com.api.userservice.utils.AppError;

@RestController
@RequestMapping("/activity")
public class UserActivityController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserTokenService userTokenService;

    @Autowired
    private UserActivityService userActivityService;

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public ResponseEntity<?> getUserActivities(HttpServletRequest request) {
        ResponseEntity<?> responseEntity = getUserByRequest(request);
        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return responseEntity;
        }
        User user = (User) responseEntity.getBody();
        return new ResponseEntity<>(userActivityService.getUserActivityListByUserId(user.getId()), HttpStatus.OK);
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
