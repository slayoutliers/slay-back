package com.slay.outliers.answer.service;

import com.slay.outliers.answer.domain.Answer;
import com.slay.outliers.answer.domain.AnswerRepository;
import com.slay.outliers.answer.service.dto.AnswerRequest;
import com.slay.outliers.answer.service.dto.AnswerResponse;
import com.slay.outliers.question.domain.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;

    @Transactional
    public AnswerResponse save(AnswerRequest answerRequest, Question question) {
        Answer answer = Answer.builder()
                .content(answerRequest.getContent())
                .question(question)
                .status(answerRequest.getStatus())
                .build();
        Answer savedAnswer = answerRepository.save(answer);
        question.addAnswer(savedAnswer);
        return AnswerResponse.of(savedAnswer);
    }

    public AnswerResponse findById(Long answerId) {
        return AnswerResponse.of(answerRepository.findById(answerId).orElseThrow(() -> new IllegalArgumentException("번호에 맞는 답변이 존재하지 않습니다.")));
    }
}
