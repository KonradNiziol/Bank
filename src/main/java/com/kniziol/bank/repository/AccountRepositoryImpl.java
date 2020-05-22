package com.kniziol.bank.repository;

import com.kniziol.bank.domain.account.Account;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class AccountRepositoryImpl implements AccountRepository {

    private static Map<Long, Account> accountsData = new ConcurrentHashMap<>();
    private static AtomicLong accountId = new AtomicLong(1);

    @Override
    public Long createAccount(String firstName, String lastName) {
        Account account = new Account(accountId.getAndIncrement(), firstName, lastName);
        return accountsData.put(account.getId(), account).getId();
    }

    @Override
    public Account findAccountById(Long id) {
        return accountsData.get(id);
    }


}
