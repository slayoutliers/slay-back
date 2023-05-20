package com.slay.outliers.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenDto {

    private String idToken;

    protected TokenDto() {
        //no-op
    }

    @Builder
    public TokenDto(String idToken) {
        this.idToken = idToken;
    }
}
