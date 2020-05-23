package com.kniziol.bank.service;

import com.kniziol.bank.repository.AccountRepository;
import com.kniziol.bank.service.dto.CreateAccountDto;
import com.kniziol.bank.service.exception.AccountCreateException;
import com.kniziol.bank.service.exception.AccountNotFoundException;
import com.kniziol.bank.service.exception.TransferMoneyException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    void createAccount_whenCreateAccountIsNull_thenThrowAccountCreateException() {
        assertThatThrownBy(() ->
                accountService.createAccount(null)
        ).isInstanceOf(AccountCreateException.class)
                .hasMessage("accountCreate.nullOrEmpty");
    }

    @Test
    void createAccount_whenCreateAccountParamIsEmpty_thenThrowAccountCreateException() {
        CreateAccountDto emptyCreateAccount = new CreateAccountDto("  ", "");
        assertThatThrownBy(() ->
                accountService.createAccount(emptyCreateAccount)
        ).isInstanceOf(AccountCreateException.class)
                .hasMessage("accountCreate.nullOrEmpty");
    }

    @Test
    void getBalanceAccount_whenEnteredIdIsNull_thenThrowAccountException() {
        assertThatThrownBy(() ->
                accountService.getBalanceAccount(null)
        ).isInstanceOf(AccountNotFoundException.class)
                .hasMessage("accountId.nullOrIncorrect");
    }
    @Test
    void getBalanceAccount_whenEnteredIdNegativeNumber_thenThrowAccountException() {
        assertThatThrownBy(() ->
                accountService.getBalanceAccount(0L)
        ).isInstanceOf(AccountNotFoundException.class)
                .hasMessage("accountId.nullOrIncorrect");
    }

    @Test
    void transferMoneyToTheAccount_whenEnteredTransferDtoIsNull_thenThrowTransferMoneyException() {
        assertThatThrownBy(() ->
                accountService.transferMoneyToTheAccount(null)
        ).isInstanceOf(TransferMoneyException.class)
                .hasMessage("account.transferEmpty");
    }

    @Test
    void withdrawMoneyFromAccount_whenEnteredTransferDtoIsNull_thenThrowTransferMoneyException() {
        assertThatThrownBy(() ->
                accountService.withdrawMoneyFromAccount(null)
        ).isInstanceOf(TransferMoneyException.class)
                .hasMessage("account.transferEmpty");
    }
}