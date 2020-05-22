package com.kniziol.bank.service;

import com.kniziol.bank.domain.account.Account;
import com.kniziol.bank.repository.AccountRepository;
import com.kniziol.bank.service.exception.AccountException;
import com.kniziol.bank.service.dto.CreateAccountDto;
import com.kniziol.bank.service.dto.TransferMoneyDto;
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
        return accountRepository.createAccount(createAccountDto.firstName(), createAccountDto.lastName());
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
        Account account = getAccountFromEnteredId(transferMoneyDto.accountId());
        account.increaseBalance(transferMoneyDto.money());
    }

    @Override
    public void withdrawMoneyFromAccount(TransferMoneyDto transferMoneyDto) {
        checkIfTransferMoneyDtoIsCorrect(transferMoneyDto);
        Account account = getAccountFromEnteredId(transferMoneyDto.accountId());
        account.decreaseBalance(transferMoneyDto.money());

    }

    private Account getAccountFromEnteredId(Long id){
        Account accountById = accountRepository.findAccountById(id);
        checkIfAccountExist(accountById, id);
        return accountById;
    }

    private String displayMoneyValue(BigDecimal value){
        return value
                .setScale(2, RoundingMode.DOWN)
                .toString();
    }

    private void checkIfAccountExist(Account account, Long enteredAccountId){
        if (Objects.isNull(account)){
            throw new AccountException(String.format("Account with selected id: %s does't exist", enteredAccountId));
        }
    }

    private void checkIfAccountIdCorrect(Long accountId){
        if (Objects.isNull(accountId) || accountId.compareTo(0L) != 1){
            throw new AccountException("Provided the wrong account number, natural number greater than zero");
        }
    }

    private void checkIfAccountIsCorrect(CreateAccountDto createAccountDto){
        if (Objects.isNull(createAccountDto) || Strings.isBlank(createAccountDto.firstName()) || Strings.isBlank(createAccountDto.lastName())){
            throw new AccountException("In created account First Name and Last Name can't be empty");
        }
    }

    private void checkIfTransferMoneyDtoIsCorrect(TransferMoneyDto transferMoneyDto){
        if (Objects.isNull(transferMoneyDto)){
            throw new TransferMoneyException("Transfer object is not present");
        }
        checkIfAccountIdCorrect(transferMoneyDto.accountId());
    }
}
