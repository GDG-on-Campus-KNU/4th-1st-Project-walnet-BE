package com.walnet.backend.domain.member.exception;

public class UnverifiedEmailException extends RuntimeException{
    public UnverifiedEmailException(String message) {
        super(message);

    }
}
