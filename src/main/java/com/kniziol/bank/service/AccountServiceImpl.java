package com.kniziol.bank.service;

import com.kniziol.bank.domain.account.Account;
import com.kniziol.bank.repository.AccountRepository;
import com.kniziol.bank.service.exception.AccountCreateException;
import com.kniziol.bank.service.dto.CreateAccountDto;
import com.kniziol.bank.service.dto.TransferMoneyDto;
import com.kniziol.bank.service.exception.AccountNotFoundException;
import com.kniziol.bank.service.exception.TransferMoneyException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public Long createAccount(CreateAccountDto createAccountDto) {
        checkIfAccountIsCorrect(createAccountDto);
        return accountRepository.createAccount(createAccountDto.getFirstName(), createAccountDto.getLastName());
    }

    @Override
    public String getBalanceAccount(Long accountId) {
        checkIfAccountIdCorrect(accountId);
        Account account = getAccountFromEnteredId(accountId);
        return displayMoneyValue(account.getBalance());
    }

    @Override
    public void transferMoneyToTheAccount(TransferMoneyDto transferMoneyDto) {
        checkIfTransferMoneyDtoIsCorrect(transferMoneyDto);
        Account account = getAccountFromEnteredId(transferMoneyDto.getAccountId());
        account.increaseBalance(transferMoneyDto.getMoney());
    }

    @Override
    public void withdrawMoneyFromAccount(TransferMoneyDto transferMoneyDto) {
        checkIfTransferMoneyDtoIsCorrect(transferMoneyDto);
        Account account = getAccountFromEnteredId(transferMoneyDto.getAccountId());
        account.decreaseBalance(transferMoneyDto.getMoney());

    }

    private Account getAccountFromEnteredId(Long id){
        Account accountById = accountRepository.findAccountById(id);
        checkIfAccountExist(accountById);
        return accountById;
    }

    private String displayMoneyValue(BigDecimal value){
        return value
                .setScale(2, RoundingMode.DOWN)
                .toString();
    }

    private void checkIfAccountExist(Account account){
        if (Objects.isNull(account)){
            throw new AccountNotFoundException("account.notFound");
        }
    }

    private void checkIfAccountIdCorrect(Long accountId){
        if (Objects.isNull(accountId) || accountId.compareTo(0L) != 1){
            throw new AccountNotFoundException("accountId.nullOrIncorrect");
        }
    }

    private void checkIfAccountIsCorrect(CreateAccountDto createAccountDto){
        if (Objects.isNull(createAccountDto) || Strings.isBlank(createAccountDto.getFirstName()) || Strings.isBlank(createAccountDto.getLastName())){
            throw new AccountCreateException("accountCreate.nullOrEmpty");
        }
    }

    private void checkIfTransferMoneyDtoIsCorrect(TransferMoneyDto transferMoneyDto){
        if (Objects.isNull(transferMoneyDto)){
            throw new TransferMoneyException("account.transferEmpty");
        }
        checkIfAccountIdCorrect(transferMoneyDto.getAccountId());
    }
}
