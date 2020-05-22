package com.kniziol.bank.service;

import com.kniziol.bank.service.dto.CreateAccountDto;
import com.kniziol.bank.service.dto.TransferMoneyDto;
import org.springframework.stereotype.Service;

@Service
public interface AccountService {

    Long createAccount(CreateAccountDto createAccountDto);
    String getBalanceAccount(Long accountId);
    void transferMoneyToTheAccount(TransferMoneyDto transferMoneyDto);
    void withdrawMoneyFromAccount(TransferMoneyDto transferMoneyDto);

}
