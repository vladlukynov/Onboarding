package com.api.courseservice.service;

import java.util.List;
import java.util.Optional;

import com.api.courseservice.DTO.CourseDTO;
import com.api.courseservice.model.Course;
import com.api.courseservice.model.Feedback;
import com.api.courseservice.model.Test;
import org.springframework.lang.NonNull;

public interface CourseService {
    List<CourseDTO> getCoursesForPost(Long postId);
    void deleteById(Long id);
    Test findNextTestInCourseById(Long id, Long userId);
    Feedback findNextFeedbackInCourseById(Long id, Long userId);
    boolean isNextTest(Long id, Long userId);
}
