package com.slay.outliers.answer.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.slay.outliers.answer.domain.Answer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnswerResponse {
    @ApiModelProperty(position = 0, example = "1(대답 고유번호)")
    private Long id;
    @ApiModelProperty(position = 1, example = "아름다운 해변에서 파도 소리를 들으며 친구들과 함께 놀았던 여름 휴가 선선한 바람과 함께하는 행복한 순간들은 지금도 마음속에 따뜻하게 남아 있어요.(답변 내용)")
    private String content;
    @ApiModelProperty(position = 2, example = "0(낮 상태 값) or 1(밤 상태 값)")
    private int status;

    @Builder
    public AnswerResponse(Long id, String content, int status) {
        this.id = id;
        this.content = content;
        this.status = status;
    }

    public static AnswerResponse of(Answer answer) {
        return AnswerResponse.builder()
                .id(answer.getId())
                .content(answer.getContent())
                .status(answer.getStatus())
                .build();
    }

    public static List<AnswerResponse> of(List<Answer> answers) {
        if(answers == null) {
            return null;
        }
        final List<AnswerResponse> answerResponseList = new ArrayList<>();
        answers
                .forEach(answer -> answerResponseList.add(AnswerResponse.of(answer)));
        return answerResponseList;
    }
}
