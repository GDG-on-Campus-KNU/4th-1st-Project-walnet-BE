package com.walnet.backend.global.exception;

import com.walnet.backend.domain.member.exception.AlreadyVerifiedException;
import com.walnet.backend.domain.member.exception.InvalidVerificationCodeException;
import com.walnet.backend.domain.member.exception.VerificationCodeExpiredException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AlreadyVerifiedException.class)
    public ResponseEntity<ErrorResponse> handleAlreadyVerified(AlreadyVerifiedException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("ALREADY_VERIFIED", e.getMessage()));
    }

    @ExceptionHandler(value = InvalidVerificationCodeException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCode(InvalidVerificationCodeException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST) // 400
                .body(new ErrorResponse("INVALID_CODE", e.getMessage()));
    }

    @ExceptionHandler(value = VerificationCodeExpiredException.class)
    public ResponseEntity<ErrorResponse> handleExpiredCode(VerificationCodeExpiredException e) {
        return ResponseEntity
                .status(HttpStatus.GONE) // 410
                .body(new ErrorResponse("EXPIRED_CODE", e.getMessage()));
    }

}
