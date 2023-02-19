package com.api.courseservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.api.courseservice.DTO.QuestionForTest;
import com.api.courseservice.model.Question;
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("SELECT new com.api.courseservice.DTO.QuestionForTest(q) FROM Question q WHERE q.test.id=:testId")
    List<QuestionForTest> getListQuestionsByTestId(Long testId);
}
