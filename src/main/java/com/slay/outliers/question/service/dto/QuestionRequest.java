package com.slay.outliers.question.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QuestionRequest {

    @ApiModelProperty(position = 0, example = "지금 떠오르는 행복한 기억이 뭔가요?(질문 내용)")
    private String content;

    protected QuestionRequest() {
        //no-op
    }
}
