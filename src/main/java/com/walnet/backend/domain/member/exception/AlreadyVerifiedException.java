package com.walnet.backend.domain.member.exception;

public class AlreadyVerifiedException extends RuntimeException{
    public AlreadyVerifiedException(String message){
        super(message);
    }
}
