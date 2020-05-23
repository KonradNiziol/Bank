package com.kniziol.bank.infrastructure.controller;

import com.kniziol.bank.infrastructure.data.ResponseData;
import com.kniziol.bank.service.AccountService;
import com.kniziol.bank.service.dto.CreateAccountDto;
import com.kniziol.bank.service.dto.TransferMoneyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/balance")
    public ResponseEntity getAccountBalance(@RequestParam Long accountId){
        return ResponseEntity.ok(
                ResponseData
                        .<String>builder()
                        .data(accountService.getBalanceAccount(accountId))
                        .build()
        );
    }

    @PostMapping("/create")
    public ResponseEntity createAccount(@RequestBody CreateAccountDto createAccountDto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseData
                        .<Long>builder()
                        .data(accountService.createAccount(createAccountDto))
                        .build());
    }

    @PutMapping("/deposit")
    public ResponseEntity increaseAccountBalance(@RequestBody TransferMoneyDto transferMoneyDto){
        accountService.transferMoneyToTheAccount(transferMoneyDto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/withdraw")
    public ResponseEntity decreaseAccountBalance(@RequestBody TransferMoneyDto transferMoneyDto){
        accountService.withdrawMoneyFromAccount(transferMoneyDto);
        return ResponseEntity.noContent().build();
    }


}
