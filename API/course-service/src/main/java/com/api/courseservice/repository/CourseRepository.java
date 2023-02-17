package com.api.courseservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.courseservice.model.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}