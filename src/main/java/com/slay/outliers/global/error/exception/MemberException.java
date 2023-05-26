package com.slay.outliers.global.error.exception;

import lombok.Getter;

@Getter
public class MemberException extends RuntimeException {

    private int status;

    public MemberException(String message, int status) {
        super(message);
        this.status = status;
    }
}
