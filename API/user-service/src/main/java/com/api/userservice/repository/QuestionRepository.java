package com.api.userservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.api.userservice.DTO.QuestionDTO;
import com.api.userservice.model.Question;
import com.api.userservice.model.User;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("SELECT new com.api.userservice.DTO.QuestionDTO(q) FROM Question q WHERE q.recipient=:user")
    List<QuestionDTO> getCompletedQuestionsForHR(User user);

    @Query("SELECT new com.api.userservice.DTO.QuestionDTO(q) FROM Question q" +
            " WHERE q.sender.team.id=:teamId AND q.recipient IS NULL")
    List<QuestionDTO> getUnfinishedQuestionsForHR(Long teamId);

    @Query("SELECT new com.api.userservice.DTO.QuestionDTO(q) FROM Question q" +
            " WHERE q.sender=:user AND q.recipient IS NOT NULL")
    List<QuestionDTO> getCompletedQuestions(User user);

    @Query("SELECT new com.api.userservice.DTO.QuestionDTO(q) FROM Question q" +
            " WHERE q.sender=:user AND q.recipient IS NULL")
    List<QuestionDTO> getUnfinishedQuestions(User user);
}
