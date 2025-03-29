package com.walnet.backend.global.exception;

import com.walnet.backend.domain.member.exception.AlreadyVerifiedException;
import com.walnet.backend.domain.member.exception.InvalidVerificationCodeException;
import com.walnet.backend.domain.member.exception.VerificationCodeExpiredException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AlreadyVerifiedException.class)
    public ErrorResponse handleAlreadyVerified(AlreadyVerifiedException e) {
        log.error("[exceptionHandle] ex", e);
        return new ErrorResponse("ALREADY_VERIFIED", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = InvalidVerificationCodeException.class)
    public ErrorResponse handleInvalidCode(InvalidVerificationCodeException e) {
        log.error("[exceptionHandle] ex", e);
        return new ErrorResponse("INVALID_CODE", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = VerificationCodeExpiredException.class)
    public ErrorResponse handleExpiredCode(VerificationCodeExpiredException e) {
        log.error("[exceptionHandle] ex", e);
        return new ErrorResponse("EXPIRED_CODE", e.getMessage());
    }
    // ✅ 4. 메일 전송 실패
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MailException.class)
    public ErrorResponse handleMailException(MailException e) {
        log.error("[exceptionHandle] ex", e);
        return new ErrorResponse("MAIL_ERROR", "이메일 전송에 실패했습니다.");
    }

    //수신할 수 없는 이메일
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MailSendException.class)
    public ErrorResponse handleMailSendException(MailSendException e) {
        log.error("[exceptionHandle] ex", e);
        return new ErrorResponse("INVALID_EMAIL", "이메일 전송에 실패했습니다. 주소를 확인해주세요.");
    }

    //이외에 전역 Exception
    // 상태 코드 지정
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    // 그냥 exception 사용
    public ErrorResponse exHandle(Exception e) {
        log.error("[exceptionHandle] ex", e);
        return new ErrorResponse("EX", "내부 오류");
    }

}
