package com.walnet.backend.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ProblemDetail> handleBusinessException(BusinessException e) {
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(ProblemDetailHelper.from(e.getErrorCode()));
    }

    @ExceptionHandler(MailException.class)
    public ResponseEntity<ProblemDetail> handleMailException(MailException e) {
        return ResponseEntity
                .badRequest()
                .body(ProblemDetailHelper.of(HttpStatus.BAD_REQUEST, "EMAIL_ERROR", "이메일 전송에 실패했습니다."));
    }


    @ExceptionHandler(MailSendException.class)
    public ResponseEntity<ProblemDetail> handleMailSendException(MailSendException e) {
        return ResponseEntity
                .badRequest()
                .body(ProblemDetailHelper.of(HttpStatus.BAD_REQUEST, "INVALID_EMAIL", "이메일 전송에 실패했습니다. 주소를 확인해주세요"));
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
