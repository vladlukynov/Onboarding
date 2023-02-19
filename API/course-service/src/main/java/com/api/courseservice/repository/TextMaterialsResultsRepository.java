package com.api.courseservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.api.courseservice.model.TextMaterials;
import com.api.courseservice.model.TextMaterialsResults;

@Repository
public interface TextMaterialsResultsRepository extends JpaRepository<TextMaterialsResults, Long> {

    @Query("SELECT t.textMaterials FROM TextMaterialsResults t WHERE t.textMaterials.block.id=:blockId AND t.userId=:userId")
    List<TextMaterials> findTextMaterialsByUserIdAndBlockId(Long userId, Long blockId);

    @Query("SELECT count(t.textMaterials) FROM TextMaterialsResults t" +
            " WHERE t.textMaterials.block.course.id=:courseId AND t.userId=:userId")
    int countTextMaterialsByUserIdAndCourseId(Long userId, Long courseId);
}
