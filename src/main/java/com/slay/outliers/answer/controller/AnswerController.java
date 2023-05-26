package com.slay.outliers.answer.controller;

import com.slay.outliers.answer.service.AnswerService;
import com.slay.outliers.answer.service.dto.AnswerRequest;
import com.slay.outliers.answer.service.dto.AnswerResponse;
import com.slay.outliers.member.service.dto.MemberResponse;
import com.slay.outliers.question.domain.Question;
import com.slay.outliers.question.service.QuestionService;
import com.slay.outliers.session.SessionConst;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class AnswerController {

    private final QuestionService questionService;
    private final AnswerService answerService;

    @ApiOperation(value = "답변 생성 메서드", notes = "1. 프론트 서버로부터 헤더값으로 JSESSIONID 쿠키를 전달 받아서 검증합니다 \n2. 세션을 통해 회원의 정보를 가져옵니다.\n3. 회원의 정보 및 질문 번호와 매핑 되어 있는 특정 질문을 가져옵니다.\n4. 질문에 답변을 추가합니다.")
    @ApiImplicitParam(name = "Cookie", value = "쿠키 정보 \n\n JSESSIONID=73E687712B72CC859DCF61785F17A464", dataType = "string", paramType = "header")
    @PostMapping("/api/answers/questions/{id}")
    public ResponseEntity<AnswerResponse> save(HttpServletRequest request, @ApiParam(value = "질문 내용", required = true) @RequestBody AnswerRequest answerRequest, @ApiParam(value = "질문 번호", required = true) @PathVariable("id") Long questionId) {
        MemberResponse memberResponse = (MemberResponse) request.getSession().getAttribute(SessionConst.LOGIN);
        Question question = questionService.findById(memberResponse.getId(), questionId);
        AnswerResponse response = answerService.save(answerRequest, question);
        return ResponseEntity.ok().body(response);
    }

    @ApiOperation(value = "답변 조회 메세드", notes = "1. 답변 번호값으로 특정 답변의 값을 가져옵니다.")
    @GetMapping("/api/answers/{answerId}")
    public ResponseEntity<AnswerResponse> findById(@ApiParam(value = "답변 번호", required = true) @PathVariable("answerId") Long answerId) {
        AnswerResponse answerResponse = answerService.findById(answerId);
        return ResponseEntity.ok().body(answerResponse);
    }
}
