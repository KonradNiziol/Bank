package com.kniziol.bank.repository;

import com.kniziol.bank.domain.account.Account;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AccountRepositoryImpl implements AccountRepository {

    private static Map<Long, Account> accountsData = new ConcurrentHashMap<>();

    @Override
    public Long createAccount(Account account) {

        return null;
    }

    @Override
    public Account findAccountById(Long id) {
        return null;
    }
}
