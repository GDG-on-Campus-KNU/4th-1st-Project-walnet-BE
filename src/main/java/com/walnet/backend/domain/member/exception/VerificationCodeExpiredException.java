package com.walnet.backend.domain.member.exception;

public class VerificationCodeExpiredException extends RuntimeException{
    public VerificationCodeExpiredException(String message) {
        super(message);
    }
}
