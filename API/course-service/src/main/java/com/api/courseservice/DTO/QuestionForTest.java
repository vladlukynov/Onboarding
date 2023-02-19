package com.api.courseservice.DTO;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.api.courseservice.model.AnswerVars;
import com.api.courseservice.model.Question;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionForTest {
    private Long id;

    private String content;

    private String rightAnswer;

    private Set<String> answers;

    public QuestionForTest(Question question) {
        this.id = question.getId();
        this.content = question.getContent();
        this.rightAnswer = question.getRightAnswer();
        this.answers = new HashSet<>();
        List<AnswerVars> answerVarsSer = question.getAnswers();
        for (AnswerVars answerVars : answerVarsSer) {
            answers.add(answerVars.getVariant());
        }
    }
}
