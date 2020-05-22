package com.kniziol.bank.service.exception;

public class AccountException extends RuntimeException {
    public AccountException(String message){
        super(message);
    }
}
