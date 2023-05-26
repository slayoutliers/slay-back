package com.slay.outliers.global.error;

import com.slay.outliers.global.error.exception.ErrorResponse;
import com.slay.outliers.global.error.exception.MismatchCookieException;
import com.slay.outliers.global.error.exception.MismatchTokenException;
import com.slay.outliers.global.error.exception.NotExistsTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class SlayExceptionHandler {

    private static final long EXCEPTION_ID = 100L;
    private static final long RUNTIME_EXCEPTION_ID = 200L;
    private static final ErrorResponse ERROR_RESPONSE = new ErrorResponse(201L, HttpStatus.BAD_REQUEST, Collections.singletonList("잘못된 요청입니다."));

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> notExistsTokenHandler(NotExistsTokenException notExistsTokenException) {
        HttpStatus httpStatus = HttpStatus.valueOf(notExistsTokenException.getStatus());
        List<String> messages = List.of(notExistsTokenException.getMessage());
        ErrorResponse errorResponse = ErrorResponse.of(notExistsTokenException.getId(), httpStatus, messages);
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> mismatchTokenHandler(MismatchTokenException mismatchTokenException) {
        HttpStatus httpStatus = HttpStatus.valueOf(mismatchTokenException.getStatus());
        List<String> messages = List.of(mismatchTokenException.getMessage());
        ErrorResponse errorResponse = ErrorResponse.of(mismatchTokenException.getId(), httpStatus, messages);
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> mismatchCookieHandler(MismatchCookieException mismatchCookieException) {
        HttpStatus httpStatus = HttpStatus.valueOf(mismatchCookieException.getStatus());
        List<String> messages = List.of(mismatchCookieException.getMessage());
        ErrorResponse errorResponse = ErrorResponse.of(mismatchCookieException.getId(), httpStatus, messages);
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> runtimeExceptionHandler(RuntimeException runtimeException) {
        try {
            HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
            List<String> messages = List.of(runtimeException.getMessage());
            ErrorResponse errorResponse = ErrorResponse.of(RUNTIME_EXCEPTION_ID, httpStatus, messages);
            return ResponseEntity.status(httpStatus).body(errorResponse);
        } catch (RuntimeException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ERROR_RESPONSE);
        }
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception exception) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        List<String> messages = List.of(exception.getMessage());
        ErrorResponse errorResponse = ErrorResponse.of(EXCEPTION_ID, httpStatus, messages);
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> illegalArgumentExceptionHandler(IllegalArgumentException illegalArgumentException) {
        try {
            HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            List<String> messages = List.of(illegalArgumentException.getMessage());
            ErrorResponse errorResponse = ErrorResponse.of(EXCEPTION_ID, httpStatus, messages);
            return ResponseEntity.status(httpStatus).body(errorResponse);
        } catch (RuntimeException runtimeException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ERROR_RESPONSE);
        }

    }
}
