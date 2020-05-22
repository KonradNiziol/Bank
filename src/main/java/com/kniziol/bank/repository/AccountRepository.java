package com.kniziol.bank.repository;

import com.kniziol.bank.domain.account.Account;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface AccountRepository {

    Long createAccount(Account account);
    Account findAccountById(Long id);


}


