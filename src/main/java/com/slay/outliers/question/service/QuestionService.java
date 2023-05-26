package com.slay.outliers.question.service;

import com.slay.outliers.question.domain.Question;
import com.slay.outliers.question.domain.QuestionRepository;
import com.slay.outliers.question.service.dto.QuestionRequest;
import com.slay.outliers.question.service.dto.QuestionResponse;
import com.slay.outliers.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Transactional
    public QuestionResponse save(QuestionRequest questionRequest, Member member) {
        Question question = Question.builder()
                .content(questionRequest.getContent())
                .member(member)
                .build();
        Question savedQuestion = questionRepository.save(question);
        member.addAnswer(savedQuestion);
    return QuestionResponse.of(question);
    }

    public List<QuestionResponse> findAllByMemberId(Long id) {
        List<Question> questions = questionRepository.findAllByMemberId(id);
        return QuestionResponse.of(questions);
    }

    public Question findById(Long memberId, Long questionId) {
        return questionRepository.findById(memberId, questionId).orElseThrow(() -> new IllegalArgumentException("번호에 맞는 질문이 존재하지 않습니다."));
    }

    @Transactional
    public void updateMood(Question question, String mood) {
        question.changeMood(mood);
    }
}
