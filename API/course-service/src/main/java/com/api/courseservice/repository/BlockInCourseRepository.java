package com.api.courseservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.courseservice.model.BlockInCourse;

public interface BlockInCourseRepository extends JpaRepository<BlockInCourse, Long> {
    List<BlockInCourse> getBlockInCoursesByCourse_Id(Long id);
}
