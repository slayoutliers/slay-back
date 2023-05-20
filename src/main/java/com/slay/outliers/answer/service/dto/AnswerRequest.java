package com.slay.outliers.answer.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AnswerRequest {
    private String content;

    protected AnswerRequest() {
        //no-op
    }
}
