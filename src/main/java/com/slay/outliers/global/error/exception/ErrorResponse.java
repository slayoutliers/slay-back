package com.slay.outliers.global.error.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class ErrorResponse {

    private Long id;
    private HttpStatus status;
    private List<String> errorMessages;

    public ErrorResponse(Long id, HttpStatus status, List<String> errorMessages) {
        this.id = id;
        this.status = status;
        this.errorMessages = errorMessages;
    }

    public static ErrorResponse of(Long id, HttpStatus status, List<String> errorMessages) {
        return new ErrorResponse(id, status, errorMessages);
    }
}
