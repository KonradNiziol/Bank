package com.kniziol.bank.infrastructure.controller;

import com.kniziol.bank.domain.exception.MoneyException;
import com.kniziol.bank.infrastructure.data.ResponseData;
import com.kniziol.bank.service.exception.AccountCreateException;
import com.kniziol.bank.service.exception.AccountNotFoundException;
import com.kniziol.bank.service.exception.TransferMoneyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {


    @ExceptionHandler(AccountCreateException.class)
    public ResponseEntity AccountCreateExceptionHandler(AccountCreateException ex){
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ResponseData
                        .<String>builder()
                        .error(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity AccountNotFoundExceptionHandler(AccountNotFoundException ex){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ResponseData
                        .<String>builder()
                        .error(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(TransferMoneyException.class)
    public ResponseEntity TransferMoneyExceptionHandler(TransferMoneyException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseData
                        .<String>builder()
                        .error(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(MoneyException.class)
    public ResponseEntity MoneyExceptionHandler(MoneyException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseData
                        .<String>builder()
                        .error(ex.getMessage())
                        .build());
    }


}
