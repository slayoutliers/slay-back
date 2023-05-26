package com.slay.outliers.global.error.exception;

import com.slay.outliers.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class MismatchCookieException extends MemberException {

    private final Long id;
    public MismatchCookieException(ErrorCode errorCode) {
        super(errorCode.getErrorMessage(), errorCode.getStatus());
        this.id = errorCode.getId();
    }
}
