package com.slay.outliers.question.domain;

import com.slay.outliers.answer.domain.Answer;
import com.slay.outliers.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "QUESTION_ID")
    private Long id;
    private String content;
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
    @OneToMany(mappedBy = "question", cascade = {CascadeType.PERSIST}, orphanRemoval = true)
    private List<Answer> answers = new ArrayList<>();
    private String mood;
    private LocalDateTime dateTime;

    @Builder
    public Question(Long id, String content, Member member, List<Answer> answers, String mood) {
        this.id = id;
        this.content = content;
        this.member = member;
        this.answers = answers;
        this.mood = mood;
        this.dateTime = LocalDateTime.now();
    }

    public void addAnswer(Answer answer) {
        answers.add(answer);
    }

    public void changeMood(String mood) {
        this.mood = mood;
    }
}
