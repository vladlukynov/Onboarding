package com.api.courseservice.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.ClientHttpResponseStatusCodeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.api.courseservice.service.CourseService;
import com.api.courseservice.service.client.UserRestTemplateClient;
import com.api.courseservice.utils.AppError;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserRestTemplateClient userRestTemplateClient;

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public ResponseEntity<?> getCourses(HttpServletRequest request) {
        try {
            Long userId = userRestTemplateClient.getUserId(request);
            Long postId = userRestTemplateClient.getUserPostId(userId, request);
            return new ResponseEntity<>(courseService.getCoursesForPost(postId), HttpStatus.OK);
        } catch (ClientHttpResponseStatusCodeException exception) {
            try {
                return new ResponseEntity<>(exception.getResponse().getStatusCode());
            } catch (IOException e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

}
