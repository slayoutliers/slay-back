package com.slay.outliers.question.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MoodDto {

    private String mood;

    protected MoodDto() {
        //no-op
    }
}
