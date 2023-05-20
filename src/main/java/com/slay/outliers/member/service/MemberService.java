package com.slay.outliers.member.service;

import com.slay.outliers.member.domain.Member;
import com.slay.outliers.member.domain.MemberRepository;
import com.slay.outliers.member.service.dto.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResponse save(String email, String name) {
        Member member = Member.builder()
                .email(email)
                .name(name)
                .build();
        return MemberResponse.of(memberRepository.save(member));
    }

    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }
}
