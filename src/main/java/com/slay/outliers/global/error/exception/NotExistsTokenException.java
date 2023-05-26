package com.slay.outliers.global.error.exception;

import com.slay.outliers.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class NotExistsTokenException extends MemberException {

    private final Long id;

    public NotExistsTokenException(ErrorCode errorCode) {
        super(errorCode.getErrorMessage(), errorCode.getStatus());
        this.id = errorCode.getId();
    }
}
