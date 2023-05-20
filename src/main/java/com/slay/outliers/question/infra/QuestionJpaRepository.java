package com.slay.outliers.question.infra;

import com.slay.outliers.question.domain.Question;
import com.slay.outliers.question.domain.QuestionRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionJpaRepository extends JpaRepository<Question, Long>, QuestionRepository {

    Question save(Question question);

    @Query("select q from Question q order by q.id desc")
    List<Question> findAll();
}
