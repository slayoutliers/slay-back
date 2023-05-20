package com.slay.outliers.answer.service;

import com.slay.outliers.answer.domain.Answer;
import com.slay.outliers.answer.domain.AnswerRepository;
import com.slay.outliers.answer.service.dto.AnswerRequest;
import com.slay.outliers.answer.service.dto.AnswerResponse;
import com.slay.outliers.question.domain.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;

    public AnswerResponse save(AnswerRequest answerRequest, Question question) {
        Answer answer = Answer.builder()
                .content(answerRequest.getContent())
                .question(question)
                .build();
        Answer savedAnswer = answerRepository.save(answer);
        question.addAnswer(savedAnswer);
        return AnswerResponse.of(savedAnswer);
    }
}
