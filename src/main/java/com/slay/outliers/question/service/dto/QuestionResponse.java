package com.slay.outliers.question.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.slay.outliers.answer.service.dto.AnswerResponse;
import com.slay.outliers.question.domain.Question;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionResponse {
    @ApiModelProperty(position = 0, example = "1(질문 고유번호)")
    private Long id;
    @ApiModelProperty(position = 1, example = "지금 떠오르는 행복한 기억이 뭔가요?(질문 내용)")
    private String content;
    @ApiModelProperty(position = 2)
    private List<AnswerResponse> answers;
    @ApiModelProperty(position = 3, example = "happy(기분)")
    private String mood;

    @Builder
    public QuestionResponse(Long id, String content, List<AnswerResponse> answers, String mood) {
        this.id = id;
        this.content = content;
        this.answers = answers;
        this.mood = mood;
    }

    public static QuestionResponse of(Question question) {
        return QuestionResponse.builder()
                .id(question.getId())
                .content(question.getContent())
                .answers(AnswerResponse.of(question.getAnswers()))
                .mood(question.getMood())
                .build();
    }

    public static List<QuestionResponse> of(List<Question> questions) {
        final List<QuestionResponse> questionResponseList = new ArrayList<>();
        questions
                .forEach(question -> questionResponseList.add(QuestionResponse.of(question)));
        return questionResponseList;
    }
}
