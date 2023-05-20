package com.slay.outliers.question.domain;

import java.util.List;

public interface QuestionRepository {
    Question save(Question question);

    List<Question> findAll();
}
