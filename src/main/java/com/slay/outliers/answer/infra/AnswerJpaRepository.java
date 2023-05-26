package com.slay.outliers.answer.infra;

import com.slay.outliers.answer.domain.Answer;
import com.slay.outliers.answer.domain.AnswerRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnswerJpaRepository extends JpaRepository<Answer, Long>, AnswerRepository {

    Answer save(Answer answer);

    Optional<Answer> findById(Long answerId);
}
