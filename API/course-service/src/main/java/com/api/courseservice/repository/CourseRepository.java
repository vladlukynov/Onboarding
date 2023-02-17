package com.api.courseservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.api.courseservice.DTO.CourseDTO;
import com.api.courseservice.model.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query("select c from Course c where c.id = ?1")
    Optional<Course> findNextTestInCourseById(@NonNull Long id);

    @Query("SELECT new com.api.courseservice.DTO.CourseDTO(c.id, c.name, c.photoSrc) FROM Course c " +
            "WHERE c.accessPostId =: postId")
    List<CourseDTO> getCoursesByPostId(Long postId);

    void deleteById(Long id);
}
