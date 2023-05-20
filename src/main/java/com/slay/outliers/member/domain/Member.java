package com.slay.outliers.member.domain;

import com.slay.outliers.question.domain.Question;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;
    private String email;
    private String name;
    @OneToMany(mappedBy = "member", cascade = {CascadeType.PERSIST}, orphanRemoval = true)
    private List<Question> questions = new ArrayList<>();

    @Builder
    public Member(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public void addAnswer(Question question) {
        questions.add(question);
    }
}
