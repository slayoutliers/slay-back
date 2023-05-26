package com.slay.outliers.question.domain;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository {
    Question save(Question question);

    List<Question> findAllByMemberId(Long id);

    Optional<Question> findById(Long memberId, Long questionId);
}
