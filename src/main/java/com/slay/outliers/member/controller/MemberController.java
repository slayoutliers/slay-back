package com.slay.outliers.member.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.slay.outliers.member.domain.Member;
import com.slay.outliers.member.dto.TokenDto;
import com.slay.outliers.member.service.MemberService;
import com.slay.outliers.member.service.dto.MemberResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
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

    @PostMapping("/api/oauth/google/members")
    public ResponseEntity<MemberResponse> verifyTokenAndSaveMember(@RequestBody TokenDto tokenDto) {
        MemberResponse memberResponse = verifyToken(tokenDto.getIdToken());
        return ResponseEntity.created(URI.create("members/" + memberResponse.getId())).body(memberResponse);
    }

    private MemberResponse verifyToken(String idToken) {
        try {
            GoogleIdToken idTokenObj = verifier.verify(idToken);
            if (idTokenObj == null) {
                return null;
                //TODO 예외 처리
            }
            GoogleIdToken.Payload payload = idTokenObj.getPayload();
            String email = payload.getEmail();
            Optional<Member> member = memberService.findByEmail(email);
            if (member.isPresent()) {
                return MemberResponse.of(member.get());
            }
            return memberService.save(email, (String) payload.get("name"));
        } catch (GeneralSecurityException | IOException e) {
            return null;
            //TODO 예외 처리
        }
    }
}
