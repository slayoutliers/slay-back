package com.slay.outliers.global.error.exception;

import com.slay.outliers.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class AuthenticateException extends MemberException {

    private final Long id;

    public AuthenticateException(ErrorCode errorCode) {
        super(errorCode.getErrorMessage(), errorCode.getStatus());
        this.id = errorCode.getId();
    }
}
