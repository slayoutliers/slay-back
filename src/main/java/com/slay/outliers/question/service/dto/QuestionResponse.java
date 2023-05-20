package com.slay.outliers.question.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.slay.outliers.answer.domain.Answer;
import com.slay.outliers.answer.service.dto.AnswerResponse;
import com.slay.outliers.question.domain.Question;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionResponse {

    private Long id;
    private String content;
    private List<AnswerResponse> answers;

    @Builder
    public QuestionResponse(Long id, String content, List<AnswerResponse> answers) {
        this.id = id;
        this.content = content;
        this.answers = answers;
    }

    public static QuestionResponse of(Question question) {
        return QuestionResponse.builder()
                .id(question.getId())
                .content(question.getContent())
                .answers(AnswerResponse.of(question.getAnswers()))
                .build();
    }

    public static List<QuestionResponse> of(List<Question> questions) {
        final List<QuestionResponse> questionResponseList = new ArrayList<>();
        questions
                .forEach(question -> questionResponseList.add(QuestionResponse.of(question)));
        return questionResponseList;
    }
}
