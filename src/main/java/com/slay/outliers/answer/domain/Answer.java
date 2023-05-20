package com.slay.outliers.answer.domain;

import com.slay.outliers.question.domain.Question;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Answer {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ANSWER_ID")
    private Long id;
    private String content;
    @ManyToOne
    @JoinColumn(name = "QUESTION_ID")
    private Question question;

    @Builder
    public Answer(Long id, String content, Question question) {
        this.id = id;
        this.content = content;
        this.question = question;
    }
}
