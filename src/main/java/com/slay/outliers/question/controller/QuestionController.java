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
import io.swagger.annotations.*;
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

    @ApiOperation(value = "질문 저장 메서드", notes = "1. 프론트 서버로부터 헤더값으로 JSESSIONID 쿠키를 전달 받아서 검증합니다 \n2. 세션을 통해 회원의 정보를 가져옵니다.\n3. 회원의 정보와 사용자가 결정한 질문을 매핑해서 저장합니다.")
    @ApiImplicitParam(name = "Cookie", value = "쿠키 정보 \n\n JSESSIONID=73E687712B72CC859DCF61785F17A464", dataType = "string", paramType = "header")
    @PostMapping("api/questions")
    public ResponseEntity<QuestionResponse> save(HttpServletRequest request, @ApiParam(value = "사용자가 선택한 질문", required = true) @RequestBody QuestionRequest questionRequest) {
        MemberResponse memberResponse = (MemberResponse) request.getSession().getAttribute(SessionConst.LOGIN);
        Member member = memberService.findById(memberResponse.getId());
        QuestionResponse response = questionService.save(questionRequest, member);
        return ResponseEntity.ok().body(response);
    }
    @ApiOperation(value = "질문 전체 조회 메서드", notes = "1. 프론트 서버로부터 헤더값으로 JSESSIONID 쿠키를 전달 받아서 검증합니다 \n2. 세션을 통해 회원의 정보를 가져옵니다.\n3. 회원의 정보와 매핑 되어 있는 질문 전체를 가져옵니다.")
    @ApiImplicitParam(name = "Cookie", value = "쿠키 정보 \n\n JSESSIONID=73E687712B72CC859DCF61785F17A464", dataType = "string", paramType = "header")
    @GetMapping("/api/questions")
    public ResponseEntity<List<QuestionResponse>> findAllByMemberId(HttpServletRequest request) {
        MemberResponse memberResponse = (MemberResponse) request.getSession().getAttribute(SessionConst.LOGIN);
        List<QuestionResponse> questionResponses = questionService.findAllByMemberId(memberResponse.getId());
        return ResponseEntity.ok().body(questionResponses);
    }

    @ApiOperation(value = "특정 질문 조회 메서드", notes = "1. 프론트 서버로부터 헤더값으로 JSESSIONID 쿠키를 전달 받아서 검증합니다 \n2. 세션을 통해 회원의 정보를 가져옵니다.\n3. 회원의 정보 및 질문 번호와 매핑 되어 있는 특정 질문을 가져옵니다.")
    @ApiImplicitParam(name = "Cookie", value = "쿠키 정보 \n\n JSESSIONID=73E687712B72CC859DCF61785F17A464", dataType = "string", paramType = "header")
    @GetMapping("/api/questions/{id}")
    public ResponseEntity<QuestionResponse> findById(HttpServletRequest request, @ApiParam(value = "질문 번호", required = true) @PathVariable(value = "id") Long questionId) {
        MemberResponse memberResponse = (MemberResponse) request.getSession().getAttribute(SessionConst.LOGIN);
        Question question = questionService.findById(memberResponse.getId(), questionId);
        QuestionResponse questionResponse = QuestionResponse.of(question);
        return ResponseEntity.ok().body(questionResponse);
    }

    @ApiOperation(value = "질문 기분 업데이트 메서드", notes = "1. 프론트 서버로부터 헤더값으로 JSESSIONID 쿠키를 전달 받아서 검증합니다 \n2. 세션을 통해 회원의 정보를 가져옵니다.\n3. 회원의 정보 및 질문 번호와 매핑 되어 있는 특정 질문을 가져옵니다.\n4.질문의 기분을 추가 하거나 수정합니다.")
    @ApiImplicitParam(name = "Cookie", value = "쿠키 정보 \n\n JSESSIONID=73E687712B72CC859DCF61785F17A464", dataType = "string", paramType = "header")
    @PostMapping("/api/questions/{id}/mood")
    public ResponseEntity<QuestionResponse> setMood(HttpServletRequest request, @ApiParam(value = "질문 번호", required = true) @PathVariable(value = "id") Long questionId, @ApiParam(value = "기분", required = true) @RequestBody MoodDto moodDto) {
        MemberResponse memberResponse = (MemberResponse) request.getSession().getAttribute(SessionConst.LOGIN);
        Question question = questionService.findById(memberResponse.getId(), questionId);
        questionService.updateMood(question, moodDto.getMood());
        QuestionResponse questionResponse = QuestionResponse.of(question);
        return ResponseEntity.ok().body(questionResponse);
    }
}
