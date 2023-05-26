package com.slay.outliers.global.error.exception;

import com.slay.outliers.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class GoogleException extends MemberException {

    private final Long id;

    public GoogleException(ErrorCode errorCode) {
        super(errorCode.getErrorMessage(), errorCode.getStatus());
        this.id = errorCode.getId();
    }
}
