package com.api.courseservice.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.api.courseservice.DTO.CourseDTO;
import com.api.courseservice.DTO.QuestionForTest;
import com.api.courseservice.model.Course;
import com.api.courseservice.service.CourseService;
import com.api.courseservice.service.QuestionService;
import com.api.courseservice.service.TestResultsService;
import com.api.courseservice.service.client.UserRestTemplateClient;
import com.api.courseservice.utils.AppError;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private TestResultsService testResultsService;

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
            return new ResponseEntity<>(
                    new AppError(HttpStatus.FORBIDDEN.value(),
                            "Forbidden"), HttpStatus.FORBIDDEN);
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

    @RequestMapping(path = "/started-courses", method = RequestMethod.GET)
    public ResponseEntity<?> getStartedOrPassedCourses(HttpServletRequest request) {
        Long userId = (long) -1;
        Long postId = (long) -1;
        try {
            userId = userRestTemplateClient.getUserId(request);
            postId = userRestTemplateClient.getUserPostId(userId, request);
        } catch (IllegalStateException | HttpClientErrorException.Forbidden ignored) {
            return new ResponseEntity<>(
                    new AppError(HttpStatus.FORBIDDEN.value(),
                            "Forbidden"), HttpStatus.FORBIDDEN);
        } catch (HttpClientErrorException.Unauthorized exception) {
            return new ResponseEntity<>(
                    new AppError(HttpStatus.UNAUTHORIZED.value(),
                            "Access token is expired"), HttpStatus.UNAUTHORIZED);
        }
        List<CourseDTO> list = courseService.getStartedOrPassedCoursesForUser(userId, postId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @RequestMapping(path = "/started-courses-by-id", method = RequestMethod.GET)
    public ResponseEntity<?> getStartedOrPassedCourses(@RequestParam Long id, HttpServletRequest request) {
        Long userId = (long) -1;
        Long postId = (long) -1;
        try {
            userId = userRestTemplateClient.getUserId(request);
            postId = userRestTemplateClient.getUserPostId(userId, request);
        } catch (IllegalStateException | HttpClientErrorException.Forbidden ignored) {
            return new ResponseEntity<>(
                    new AppError(HttpStatus.FORBIDDEN.value(),
                            "Forbidden"), HttpStatus.FORBIDDEN);
        } catch (HttpClientErrorException.Unauthorized exception) {
            return new ResponseEntity<>(
                    new AppError(HttpStatus.UNAUTHORIZED.value(),
                            "Access token is expired"), HttpStatus.UNAUTHORIZED);
        }
        List<CourseDTO> list = courseService.getStartedOrPassedCoursesForUser(id, postId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @RequestMapping(path = "/get-questions-for-test", method = RequestMethod.GET)
    public ResponseEntity<?> getQuestionsForTest(@RequestParam Long id, HttpServletRequest request) {
        Long userId = (long) -1;
        Long postId = (long) -1;
        try {
            userId = userRestTemplateClient.getUserId(request);
            postId = userRestTemplateClient.getUserPostId(userId, request);
        } catch (IllegalStateException | HttpClientErrorException.Forbidden ignored) {
            return new ResponseEntity<>(
                    new AppError(HttpStatus.FORBIDDEN.value(),
                            "Forbidden"), HttpStatus.FORBIDDEN);
        } catch (HttpClientErrorException.Unauthorized exception) {
            return new ResponseEntity<>(
                    new AppError(HttpStatus.UNAUTHORIZED.value(),
                            "Access token is expired"), HttpStatus.UNAUTHORIZED);
        }
        List<QuestionForTest> list = questionService.getQuestionsByTestId(id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @RequestMapping(path = "/set-results-for-test", method = RequestMethod.GET)
    public ResponseEntity<?> setResultsForTest(@RequestParam Long id, @RequestParam Integer percents, HttpServletRequest request) {
        Long userId = (long) -1;
        Long postId = (long) -1;
        try {
            userId = userRestTemplateClient.getUserId(request);
            postId = userRestTemplateClient.getUserPostId(userId, request);
        } catch (IllegalStateException | HttpClientErrorException.Forbidden ignored) {
            return new ResponseEntity<>(
                    new AppError(HttpStatus.FORBIDDEN.value(),
                            "Forbidden"), HttpStatus.FORBIDDEN);
        } catch (HttpClientErrorException.Unauthorized exception) {
            return new ResponseEntity<>(
                    new AppError(HttpStatus.UNAUTHORIZED.value(),
                            "Access token is expired"), HttpStatus.UNAUTHORIZED);
        }
        try {
            testResultsService.setResultsForTest(id, userId, percents);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(
                    new AppError(HttpStatus.NOT_FOUND.value(),
                            "This test not exists"), HttpStatus.NOT_FOUND);
        }
    }

//    @RequestMapping(path = "/next-task", method = RequestMethod.GET)
//    public ResponseEntity<?> getTask(@RequestParam("CourseId") Long courseId, HttpServletRequest request) {
//        try {
//            Long userId = userRestTemplateClient.getUserId(request);
//            boolean flag = courseService.isNextTest(courseId, userId);
//            if (flag) {
//                return new ResponseEntity<>(courseService.findNextTestInCourseById(courseId, userId), HttpStatus.OK);
//            }
//            return new ResponseEntity<>(courseService.findNextFeedbackInCourseById(courseId, userId), HttpStatus.OK);
//        } catch (Exception exception) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @RequestMapping(path = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getCourseById(@PathVariable Long id, HttpServletRequest request) {
        Long userId;
        Long postId = (long) -1;
        try {
            userId = userRestTemplateClient.getUserId(request);
            postId = userRestTemplateClient.getUserPostId(userId, request);
        } catch (IllegalStateException | HttpClientErrorException.Forbidden ignored) {
            return new ResponseEntity<>(
                    new AppError(HttpStatus.FORBIDDEN.value(),
                            "Forbidden"), HttpStatus.FORBIDDEN);
        } catch (HttpClientErrorException.Unauthorized exception) {
            return new ResponseEntity<>(
                    new AppError(HttpStatus.UNAUTHORIZED.value(),
                            "Access token is expired"), HttpStatus.UNAUTHORIZED);
        }
        try {
            if (!courseService.checkAccessUserCourse(userId, postId, id)) {
                return new ResponseEntity<>(
                        new AppError(HttpStatus.CONFLICT.value(),
                                "You don't have access for this course"), HttpStatus.CONFLICT);
            } else {
                return new ResponseEntity<>(courseService.getCourseForCoursePage(id, userId), HttpStatus.OK);
            }
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @RequestMapping(path = "/delete-by-id/{id}", method = RequestMethod.GET)
//    @ResponseBody
//    public ResponseEntity<?> getPostByUserId(@PathVariable Long id) {
//        try {
//            courseService.deleteById(id);
//            return new ResponseEntity<>(HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @RequestMapping(path = "/image", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public ResponseEntity<?> getUserImage(@RequestParam Long id) {
        Course course;
        try {
            course = courseService.getCourseById(id);
        } catch (EntityNotFoundException exception) {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity<>(
                    new AppError(HttpStatus.NOT_FOUND.value(),
                            "Course with id " + id + " does not exist."), httpHeaders, HttpStatus.NOT_FOUND);
        }
        try {
            URL res = getClass().getClassLoader().getResource("images/" + course.getPhotoSrc());
            File file;
            if (res != null) {
                file = Paths.get(res.toURI()).toFile();
                InputStream input = new FileInputStream(file);
                return new ResponseEntity<>(IOUtils.toByteArray(input), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (IOException | URISyntaxException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
