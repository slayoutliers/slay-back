package com.slay.outliers.question.infra;

import com.slay.outliers.question.domain.Question;
import com.slay.outliers.question.domain.QuestionRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface QuestionJpaRepository extends JpaRepository<Question, Long>, QuestionRepository {

    Question save(Question question);

    @Query("SELECT q FROM Question q WHERE q.member.id = :memberId")
    List<Question> findAllByMemberId(@Param("memberId") Long memberId);

    @Query("SELECT q FROM Question q WHERE q.member.id = :memberId AND q.id = :questionId")
    Optional<Question> findById(@Param("memberId") Long memberId, @Param("questionId") Long questionId);
}
