package com.api.courseservice.service;

import java.util.List;

import com.api.courseservice.DTO.CourseDTO;
import com.api.courseservice.model.Course;
import com.api.courseservice.model.Feedback;
import com.api.courseservice.model.Test;

public interface CourseService {
    List<CourseDTO> getCoursesForUser(Long userId, Long postId);
    void deleteById(Long id);
    Test findNextTestInCourseById(Long id, Long userId);
    Feedback findNextFeedbackInCourseById(Long id, Long userId);
    boolean isNextTest(Long id, Long userId);
    double getPercentageOfCompletion(Long courseId, Long userId);
    int getCountTheme(Long courseId);
    Course getCourseById(Long id);
    List<CourseDTO> getStartedOrPassedCoursesForUser(Long userId, Long postId);
}
