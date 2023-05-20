package com.slay.outliers.question.service;

import com.slay.outliers.question.domain.Question;
import com.slay.outliers.question.domain.QuestionRepository;
import com.slay.outliers.question.service.dto.QuestionRequest;
import com.slay.outliers.question.service.dto.QuestionResponse;
import com.slay.outliers.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionResponse save(QuestionRequest questionRequest, Member member) {
        Question question = Question.builder()
                .content(questionRequest.getContent())
                .member(member)
                .build();
        Question savedQuestion = questionRepository.save(question);
        member.addAnswer(savedQuestion);
    return QuestionResponse.of(question);
    }

    public List<QuestionResponse> findAll() {
        List<Question> questions = questionRepository.findAll();
        return QuestionResponse.of(questions);
    }
}
