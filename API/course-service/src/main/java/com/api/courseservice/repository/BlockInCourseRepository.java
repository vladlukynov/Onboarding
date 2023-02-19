package com.api.courseservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.courseservice.model.BlockInCourse;
@Repository
public interface BlockInCourseRepository extends JpaRepository<BlockInCourse, Long> {
    List<BlockInCourse> getBlockInCoursesByCourse_Id(Long id);
}
