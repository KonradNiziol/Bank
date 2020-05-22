package com.kniziol.bank.domain.account;

import com.kniziol.bank.domain.exception.MoneyException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class AccountTest {

    private Account account;

    @BeforeEach
    public void initAccount(){
        this.account = new Account(1L, "Konrad", "Niziol");
    }

    @Test
    void increaseBalance_whenIncrease_thenBalanceAccountOk() {
        String moneyAdd = "200";
        account.increaseBalance(moneyAdd);
        assertThat(account.getBalance()).isEqualTo(moneyAdd);
    }

    @Test
    void decreaseBalance_whenDecrease_thenBalanceAccountOk() {
        String moneyAdd = "200";
        account.decreaseBalance(moneyAdd);
        Assertions.assertThat(account.getBalance()).isEqualTo(new BigDecimal(moneyAdd).negate());
    }

    @Test
    void accountHistory_whenIncreaseAndDecrease_thenAllOperationIsInHistory(){
        List<String> numberToAdd = new ArrayList<>();
        Collections.addAll(numberToAdd,"100", "50", "200", "150");
        numberToAdd.stream().forEach(value -> {
            account.increaseBalance(value);
            account.decreaseBalance(value);
        });

        assertThat(account.getBalance()).isEqualTo(new BigDecimal("0"));
        assertThat(account.getAccountHistory().size()).isEqualTo(numberToAdd.size() * 2);
        assertThat(account.getAccountHistory().stream().reduce(BigDecimal::add).get()).isEqualTo(account.getBalance());
    }

    @ParameterizedTest
    @ValueSource(strings = {"AB", ".002", "2.3.4", "0.", "2E10", "-10"})
    public void giveValueToIncrease_whenValueFormatIsNotCorrect_thenThrowException(String value) {
        assertThatThrownBy(()->
                account.increaseBalance(value))
                .hasMessage("Money value is not correct. Floating-point positive number")
                .isInstanceOf(MoneyException.class);
    }


}