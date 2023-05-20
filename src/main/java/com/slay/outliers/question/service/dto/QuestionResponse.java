package com.slay.outliers.question.service.dto;

import com.slay.outliers.question.domain.Question;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class QuestionResponse {

    private Long id;
    private String content;

    @Builder
    public QuestionResponse(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    public static QuestionResponse of(Question question) {
        return QuestionResponse.builder()
                .id(question.getId())
                .content(question.getContent())
                .build();
    }

    public static List<QuestionResponse> of(List<Question> questions) {
        final List<QuestionResponse> questionResponseList = new ArrayList<>();
        questions
                .forEach(question -> questionResponseList.add(QuestionResponse.of(question)));
        return questionResponseList;
    }
}
