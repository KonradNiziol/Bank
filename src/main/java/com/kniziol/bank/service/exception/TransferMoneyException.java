package com.kniziol.bank.service.exception;

public class TransferMoneyException extends RuntimeException {
    public TransferMoneyException(String message){
        super(message);
    }
}
