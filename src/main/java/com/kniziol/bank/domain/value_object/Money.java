package com.kniziol.bank.domain.value_object;

import com.kniziol.bank.domain.exception.MoneyException;

import java.math.BigDecimal;
import java.util.Objects;

public record Money(BigDecimal value) {
    private static final String MONEY_REGEXP = "\\d+(\\.\\d+)?";

    public Money(String value) {
        this(init(value));
    }

    public Money add(String value) {
        return new Money(this.value.add(init(value)));
    }

    public Money add(Money money) {
        return new Money(this.value.add(money.value));
    }

    public Money multiply(int value) {
        return new Money(this.value.multiply(BigDecimal.valueOf(value)));
    }

    public Money multiply(String value) {
        return new Money(this.value.multiply(new BigDecimal(value)));
    }

    private static BigDecimal init(String value) {
        if  (Objects.isNull(value) || value.matches(MONEY_REGEXP)) {
            throw new MoneyException("Money value is not correct");
        }

        return new BigDecimal(value);
    }
}
