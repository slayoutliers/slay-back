package com.slay.outliers.answer.service.dto;

import com.slay.outliers.answer.domain.Answer;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class AnswerResponse {

    private Long id;
    private String content;

    @Builder
    public AnswerResponse(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    public static AnswerResponse of(Answer answer) {
        return AnswerResponse.builder()
                .id(answer.getId())
                .content(answer.getContent())
                .build();
    }

    public static List<AnswerResponse> of(List<Answer> answers) {
        final List<AnswerResponse> answerResponseList = new ArrayList<>();
        answers
                .forEach(answer -> answerResponseList.add(AnswerResponse.of(answer)));
        return answerResponseList;
    }
}
