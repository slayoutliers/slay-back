package com.slay.outliers.answer.controller;

import com.slay.outliers.answer.service.AnswerService;
import com.slay.outliers.answer.service.dto.AnswerRequest;
import com.slay.outliers.answer.service.dto.AnswerResponse;
import com.slay.outliers.question.domain.Question;
import com.slay.outliers.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RestController
public class AnswerController {

    private final QuestionService questionService;
    private final AnswerService answerService;

    @PostMapping("/api/answers/questions/{id}")
    public ResponseEntity<AnswerResponse> save(@RequestBody AnswerRequest answerRequest, Long questionId) {
        Question question = questionService.findById(questionId);
        AnswerResponse response = answerService.save(answerRequest, question);
        return ResponseEntity.created(URI.create("/api/answers/" + response.getId() + "/questions/" + question.getId())).body(response);
    }
}
