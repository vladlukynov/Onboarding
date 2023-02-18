package com.api.courseservice.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.ClientHttpResponseStatusCodeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import com.api.courseservice.DTO.CourseDTO;
import com.api.courseservice.service.CourseService;
import com.api.courseservice.service.client.UserRestTemplateClient;
import com.api.courseservice.utils.AppError;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserRestTemplateClient userRestTemplateClient;

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public ResponseEntity<?> getCourses(HttpServletRequest request) {
        Long userId = (long) -1;
        Long postId = (long) -1;
        try {
            userId = userRestTemplateClient.getUserId(request);
            postId = userRestTemplateClient.getUserPostId(userId, request);
        } catch (IllegalStateException | HttpClientErrorException.Forbidden ignored) {
        } catch (HttpClientErrorException.Unauthorized exception) {
            return new ResponseEntity<>(
                    new AppError(HttpStatus.UNAUTHORIZED.value(),
                            "Access token is expired"), HttpStatus.UNAUTHORIZED);
        }
        Map<String, Object> map = new HashMap<>();
        List<CourseDTO> list = courseService.getCoursesForUser(userId, postId);
        if (!list.get(list.size() - 1).isClosed()) {
            map.put("currentCourse", null);
        } else {
            int i = 0;
            for (; i < list.size(); i++) {
                if (list.get(i).isClosed()) {
                    break;
                }
            }
            map.put("currentCourse", list.get(i - 1));
        }
        map.put("allCourses", list);
        return new ResponseEntity<>(map, HttpStatus.OK);

    }

    @RequestMapping(path = "/next-task", method = RequestMethod.GET)
    public ResponseEntity<?> getTask(@RequestParam("CourseId") Long courseId, HttpServletRequest request) {
        try {
            Long userId = userRestTemplateClient.getUserId(request);
            boolean flag = courseService.isNextTest(courseId, userId);
            if (flag) {
                return new ResponseEntity<>(courseService.findNextTestInCourseById(courseId, userId), HttpStatus.OK);
            }
            return new ResponseEntity<>(courseService.findNextFeedbackInCourseById(courseId, userId), HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(path = "/delete-by-id/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getPostByUserId(@PathVariable Long id) {
        try {
            courseService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
