package com.slay.outliers.question.domain;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository {
    Question save(Question question);

    List<Question> findAll();

    Optional<Question> findById(Long id);
}
