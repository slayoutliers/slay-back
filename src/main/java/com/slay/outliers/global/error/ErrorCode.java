package com.slay.outliers.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    MISMATCH_TOKEN(HttpStatus.BAD_REQUEST.value(), "토큰에 맞는 구글 회원이 존재하지 않습니다", 300L),
    NOT_EXISTS_TOKEN(HttpStatus.BAD_REQUEST.value(), "토큰이 존재하지 않습니다", 301L),
    MISMATCH_COOKIE(HttpStatus.BAD_REQUEST.value(), "쿠키에 맞는 회원이 존재하지 않습니다", 302L),
    NOT_WORKS_GOOGLE(HttpStatus.BAD_REQUEST.value(), "구글 자체 서버의 문제입니다.", 400L);

    private int status;
    private String errorMessage;
    private Long id;

    ErrorCode(int status, String errorMessage, Long id) {
        this.status = status;
        this.errorMessage = errorMessage;
        this.id = id;
    }
}
