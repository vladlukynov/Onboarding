package com.api.courseservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.courseservice.DTO.CourseDTO;
import com.api.courseservice.repository.CourseRepository;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Override
    public List<CourseDTO> getCoursesForPost(Long postId) {
        return courseRepository.getCoursesByPostId(postId);
    }

    @Override
    public void deleteById(Long id){
        courseRepository.deleteById(id);
    }
}
