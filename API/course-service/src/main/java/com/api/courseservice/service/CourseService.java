package com.api.courseservice.service;

import java.util.List;

import com.api.courseservice.DTO.CourseDTO;

public interface CourseService {
    List<CourseDTO> getCoursesForPost(Long postId);
    void deleteById(Long id);
}
