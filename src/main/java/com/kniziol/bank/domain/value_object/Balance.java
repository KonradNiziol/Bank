package com.kniziol.bank.domain.value_object;

import java.math.BigDecimal;

public record Balance(BigDecimal value) {



    public Balance add(BigDecimal value) {
        return new Balance(this.value.add(value));
    }

    public Balance subtract(BigDecimal value) {
        return new Balance(this.value.subtract(value));
    }
}
