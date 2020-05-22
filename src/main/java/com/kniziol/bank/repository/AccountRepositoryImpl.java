package com.kniziol.bank.repository;

import com.kniziol.bank.domain.account.Account;
import com.kniziol.bank.repository.exception.AccountException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class AccountRepositoryImpl implements AccountRepository {

    private static Map<Long, Account> accountsData = new ConcurrentHashMap<>();
    private static AtomicLong accountId = new AtomicLong(1);

    @Override
    public Long createAccount(String firstName, String lastName) {
        Account account = new Account(accountId.getAndIncrement(), firstName, lastName);
        checkIfAccountIsCorrect(account);
        return accountsData.put(account.getId(), account).getId();
    }

    @Override
    public Account findAccountById(Long id) {
        return accountsData.get(id);
    }

    private void checkIfAccountIsCorrect(Account account){
        if (Strings.isBlank(account.getFirstName()) || Strings.isBlank(account.getLastName())){
            throw new AccountException("In created account First Name and Last Name can't be empty");
        }
    }
}
