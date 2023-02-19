package com.api.courseservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.api.courseservice.model.Test;

public interface TestRepository extends JpaRepository<Test, Long> {
    @Query("SELECT t FROM Test t WHERE t.block.course.id=:courseId")
    List<Test> getByCourse(Long courseId);

    @Query("SELECT t FROM Test t WHERE t.block.id=:blockId")
    List<Test> getByBlock(Long blockId);

    @Query("SELECT COALESCE(count(t), 0) FROM Test t WHERE t.block.course.id=:courseId")
    Integer getCountTestByCourse(Long courseId);
}