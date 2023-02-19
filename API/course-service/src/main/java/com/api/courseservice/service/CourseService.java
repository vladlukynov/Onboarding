package com.api.courseservice.service;

import java.util.List;

import com.api.courseservice.DTO.CourseDTO;
import com.api.courseservice.DTO.CoursePage;
import com.api.courseservice.model.Course;

public interface CourseService {
    List<CourseDTO> getCoursesForUser(Long userId, Long postId);
    //void deleteById(Long id);
    //Test findNextTestInCourseById(Long id, Long userId);
    //Feedback findNextFeedbackInCourseById(Long id, Long userId);
   // boolean isNextTest(Long id, Long userId);
    double getPercentageOfCompletion(Long courseId, Long userId);
    int getCountTheme(Long courseId);
    Course getCourseById(Long id);
    List<CourseDTO> getStartedOrPassedCoursesForUser(Long userId, Long postId);
    CoursePage getCourseForCoursePage(Long courseId, Long userId);
    boolean checkAccessUserCourse(Long userId, Long postId, Long courseId);
}
