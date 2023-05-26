package com.slay.outliers.member.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.slay.outliers.global.error.ErrorCode;
import com.slay.outliers.global.error.exception.GoogleException;
import com.slay.outliers.global.error.exception.MismatchCookieException;
import com.slay.outliers.global.error.exception.MismatchTokenException;
import com.slay.outliers.global.error.exception.NotExistsTokenException;
import com.slay.outliers.member.domain.Member;
import com.slay.outliers.member.service.MemberService;
import com.slay.outliers.member.service.dto.MemberResponse;
import com.slay.outliers.session.SessionConst;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Optional;

@PropertySource("classpath:google.properties")
@RestController
public class MemberController {

    private final MemberService memberService;
    private final GoogleIdTokenVerifier verifier;

    public MemberController(@Value("${google.client-id}") String clientId, MemberService memberService) {
        this.memberService = memberService;
        NetHttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = new JacksonFactory();
        verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(clientId))
                .build();
    }

    @ApiResponses(
            value = {
                    @ApiResponse(
                            code = 400,
                            message = "쿠키에 맞는 회원이 존재하지 않을 경우 예외 발생\n" +
                                    "에러 코드(id):302, 에러 메시지:쿠키에 맞는 회원이 존재하지 않습니다, 에러 상태코드:400\n"
                    )
            }
    )
    @ApiOperation(value = "토큰 검증 및 회원 정보 응답 메서드", notes = "1. 프론트로 서버부터 헤더값으로 JSESSIONID 쿠키를 전달 받아서 검증합니다.\n2. 세션을 통해서 회원의 정보를 가져옵니다.\n3. 회원의 정보가 없다면 예외를 발생시킵니다.\n4. 회원 정보를 프론트 서버로 전달합니다.")
    @ApiImplicitParam(name = "Cookie", value = "쿠키 정보 \n\n JSESSIONID=73E687712B72CC859DCF61785F17A464", dataType = "string", paramType = "header")
    @GetMapping("api/members")
    public ResponseEntity<MemberResponse> findMemberByCookie(HttpServletRequest request) {
        HttpSession session = request.getSession();
        MemberResponse member = (MemberResponse) session.getAttribute(SessionConst.LOGIN);
        if (member == null) {
            throw new MismatchCookieException(ErrorCode.MISMATCH_COOKIE);
        }
        return ResponseEntity.ok(member);
    }

    @ApiResponses(
            value = {
                    @ApiResponse(
                            code = 400
                            , message = "(1)토큰이 없을 경우 예외 발생\n" +
                            "에러 코드(id):301, 에러 메시지:토큰이 존재하지 않습니다, 에러 상태코드:400\n\n" +
                            "<Example Value>\n" +
                            "(2)구글 API를 통해 잘못된 토큰에 맞는 회원을 찾지 못했을 경우 예외 발생\n" +
                            "에러 코드(id):201, 잘못된 요청입니다, 에러 상태코드:400\n\n" +
                            "<Example Value>\n" +
                            "(3)구글 자체의 오류로 인한 예외 발생\n" +
                            "에러 코드(id):400, 에러 메시지:구글 자체 서버의 문제입니다, 에러 상태코드:400\n"
                    )
            }
    )
    @ApiOperation(value = "구글 검증 및 회원 관련 쿠키 응답 메서드", notes = "1. 프론트 서버로부터 헤더값으로 idToken을 전달 받아서 검증합니다.\n2. idToken이 존재하지 않으면 예외를 발생 시키고, 구글 API를 통해 idToken(잘못된 토큰)에 맞는 회원을 찾지 못하면 예외를 발생시키고 구글 서버의 문제가 발생하면 예외를 발생시킵니다.\n3. 구글 API를 통해서 회원의 정보인 이메일과 이름을 전달 받습니다.\n4. 회원의 이메일을 기반으로 이미 존재하는 회원인지 아닌지를 나눠서 회원을 생성하거나 회원 가져옵니다.\n5. 회원 정보를 Session에 담고 쿠키를 통해 프론트 서버로 전달합니다.")
    @ApiImplicitParam(name = "idToken", value = "토큰정보\n\n{\n" + "\"idToken\": \"yJhbGci...중략...JM3RkoemA\"\n" + "}", required = true, dataType = "string", paramType = "header")
    @PostMapping("/api/members/oauth/google")
    public ResponseEntity<Void> verifyTokenAndSaveOrUpdateMember(HttpServletRequest request) {
        String token = request.getHeader("idToken");
        MemberResponse memberResponse = verifyToken(token);
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN, memberResponse);
        return ResponseEntity.ok().build();
    }

    @ApiResponse(
            code = 200
            , message = "쿠키 및 세션 무효화\n" +
            "\n\n" +
            "<Example Value>\n" +
            "Cookie = null"
    )
    @ApiOperation(value = "세션 초기화 및 쿠키 제거 메서드", notes = "1. 프론트 서버로부터 쿠키 무효화 요청이 오면 서버의 세션 여부를 확인한 후, 세션을 무효화시키고 클라이언트의 쿠키를 제거 합니다.")
    @ApiImplicitParam(name = "Cookie", value = "쿠키 정보 \n\n JSESSIONID=73E687712B72CC859DCF61785F17A464", dataType = "string", paramType = "header")
    @PostMapping("api/members/invalidate-cookie")
    public ResponseEntity<Void> invalidateCookie(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok().build();
    }

    private MemberResponse verifyToken(String idToken) {
        try {
            if (idToken == null) {
                throw new NotExistsTokenException(ErrorCode.NOT_EXISTS_TOKEN);
            }
            GoogleIdToken idTokenObj = verifier.verify(idToken);
            if (idTokenObj == null) {
                throw new MismatchTokenException(ErrorCode.MISMATCH_TOKEN);
            }
            GoogleIdToken.Payload payload = idTokenObj.getPayload();
            String email = payload.getEmail();
            Optional<Member> member = memberService.findByEmail(email);
            if (member.isPresent()) {
                return MemberResponse.of(member.get());
            }
            return memberService.save(email, (String) payload.get("name"));
        } catch (GeneralSecurityException | IOException e) {
            throw new GoogleException(ErrorCode.NOT_WORKS_GOOGLE);
        }
    }
}
