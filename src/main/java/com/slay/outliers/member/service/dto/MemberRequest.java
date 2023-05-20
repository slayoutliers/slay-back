package com.slay.outliers.member.service.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberRequest {
    private String email;
    private String name;

    @Builder
    public MemberRequest(String email, String name) {
        this.email = email;
        this.name = name;
    }
}
