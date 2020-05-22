package com.kniziol.bank.domain.account;

import com.kniziol.bank.domain.exception.MoneyException;
import com.kniziol.bank.domain.value_object.Balance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Account {

    private static final String POSITIVE_NUMBER_REGEXP = "\\d+(\\.\\d+)?";

    private long id;
    private String firstName;
    private String lastName;
    private Balance balance = new Balance(new BigDecimal("0"));
    private List<BigDecimal> history = new ArrayList<>();

    public Account(Long id, String firstName, String lastName){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public BigDecimal getBalance(){
        return balance.value();
    }

    public List<BigDecimal> getAccountHistory(){
        return Collections.unmodifiableList(history);
    }

    public void increaseBalance(String value){
        BigDecimal money = convertToNumber(value);
        this.balance = balance.add(money);
        updateHistory(money.negate());
    }

    public void decreaseBalance(String value){
        BigDecimal money = convertToNumber(value);
        this.balance = balance.subtract(money);
        updateHistory(money.negate());
    }

    private void updateHistory(BigDecimal value){
        history.add(value);
    }

    private BigDecimal convertToNumber(String value) {
        validateIfPositiveNumber(value);
        return new BigDecimal(value);
    }

    private void validateIfPositiveNumber(String value){
        if  (Objects.isNull(value) || value.matches(POSITIVE_NUMBER_REGEXP)) {
            throw new MoneyException("Money value is not correct. Floating-point positive number ");
        }
    }

}
