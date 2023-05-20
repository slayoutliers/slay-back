package com.slay.outliers.question.controller;

import com.slay.outliers.member.domain.Member;
import com.slay.outliers.member.service.MemberService;
import com.slay.outliers.question.service.QuestionService;
import com.slay.outliers.question.service.dto.QuestionRequest;
import com.slay.outliers.question.service.dto.QuestionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class QuestionController {

    private final QuestionService questionService;
    private final MemberService memberService;

    @PostMapping("api/questions/members/{id}")
    public ResponseEntity<QuestionResponse> save(@RequestBody QuestionRequest questionRequest, @PathVariable Long memberId) {
        Member member = memberService.findById(memberId);
        QuestionResponse response = questionService.save(questionRequest, member);
        return ResponseEntity.created(URI.create("api/questions/" + response.getId() + "/members/" + member.getId())).build();
    }

    @GetMapping("/api/questions")
    public ResponseEntity<List<QuestionResponse>> findAll() {
        List<QuestionResponse> questionResponses = questionService.findAll();
        return ResponseEntity.ok().body(questionResponses);
    }
}
