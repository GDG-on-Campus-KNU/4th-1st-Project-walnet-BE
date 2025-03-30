package com.walnet.backend.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class ProblemDetailHelper {

    public static ProblemDetail from(ErrorCode errorCode) {
        ProblemDetail problem = ProblemDetail.forStatus(errorCode.getStatus());
        problem.setTitle(errorCode.name());
        problem.setDetail(errorCode.getMessage());
        return problem;
    }

    public static ProblemDetail of(HttpStatus status, String title, String detail) {
        ProblemDetail problem = ProblemDetail.forStatus(status);
        problem.setTitle(title);
        problem.setDetail(detail);
        return problem;
    }

    // 예외 메시지를 추가로 넣고 싶을 때
    public static ProblemDetail withCause(ErrorCode errorCode, Throwable cause) {
        ProblemDetail problem = from(errorCode);
        problem.setProperty("exception", cause.getClass().getSimpleName());
        problem.setProperty("message", cause.getMessage());
        return problem;
    }
}
