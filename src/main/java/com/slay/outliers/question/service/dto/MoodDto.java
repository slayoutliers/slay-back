package com.slay.outliers.question.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MoodDto {
    @ApiModelProperty(position = 0, example = "happy, peaceful, sad, angry, worry 중 택1(기분)")
    private String mood;

    protected MoodDto() {
        //no-op
    }
}
