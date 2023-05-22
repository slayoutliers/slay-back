package com.slay.outliers.question.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QuestionRequest {

    private String content;

    protected QuestionRequest() {
        //no-op
    }
}
