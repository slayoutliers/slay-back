package com.slay.outliers.answer.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AnswerRequest {
    @ApiModelProperty(position = 0, example = "아름다운 해변에서 파도 소리를 들으며 친구들과 함께 놀았던 여름 휴가 선선한 바람과 함께하는 행복한 순간들은 지금도 마음속에 따뜻하게 남아 있어요.(답변 내용)")
    private String content;
    @ApiModelProperty(position = 1, example = "0(낮 상태 값) or 1(밤 상태 값)")
    private int status;

    protected AnswerRequest() {
        //no-op
    }
}
