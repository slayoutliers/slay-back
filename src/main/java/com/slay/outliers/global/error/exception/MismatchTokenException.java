package com.slay.outliers.global.error.exception;

import com.slay.outliers.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class MismatchTokenException extends MemberException {

    private final Long id;
    public MismatchTokenException(ErrorCode errorCode) {
        super(errorCode.getErrorMessage(), errorCode.getStatus());
        this.id = errorCode.getId();
    }
}
