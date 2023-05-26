package com.slay.outliers.question.controller;

import com.slay.outliers.member.domain.Member;
import com.slay.outliers.member.service.MemberService;
import com.slay.outliers.member.service.dto.MemberResponse;
import com.slay.outliers.question.domain.Question;
import com.slay.outliers.question.service.QuestionService;
import com.slay.outliers.question.service.dto.MoodDto;
import com.slay.outliers.question.service.dto.QuestionRequest;
import com.slay.outliers.question.service.dto.QuestionResponse;
import com.slay.outliers.session.SessionConst;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class QuestionController {

    private final QuestionService questionService;
    private final MemberService memberService;

    @PostMapping("api/questions")
    public ResponseEntity<QuestionResponse> save(HttpServletRequest request, @RequestBody QuestionRequest questionRequest) {
        MemberResponse memberResponse = (MemberResponse) request.getSession().getAttribute(SessionConst.LOGIN);
        Member member = memberService.findById(memberResponse.getId());
        QuestionResponse response = questionService.save(questionRequest, member);
        return ResponseEntity.ok().body(response);
    }


    @GetMapping("/api/questions")
    public ResponseEntity<List<QuestionResponse>> findAll() {
        List<QuestionResponse> questionResponses = questionService.findAll();
        return ResponseEntity.ok().body(questionResponses);
    }

    @GetMapping("/api/questions/{id}")
    public ResponseEntity<QuestionResponse> findById(@PathVariable Long id) {
        Question question = questionService.findById(id);
        QuestionResponse questionResponse = QuestionResponse.of(question);
        return ResponseEntity.ok().body(questionResponse);
    }

    @PostMapping("/api/questions/{id}/mood")
    public ResponseEntity<Void> setMood(@PathVariable Long id, @RequestBody MoodDto moodDto) {
        Question question = questionService.findById(id);
        question.changeMood(moodDto.getMood());
        return ResponseEntity.ok().build();
    }
}
