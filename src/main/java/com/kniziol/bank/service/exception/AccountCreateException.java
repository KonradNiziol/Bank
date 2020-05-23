package com.kniziol.bank.service.exception;

public class AccountCreateException extends RuntimeException {
    public AccountCreateException(String message){
        super(message);
    }
}
