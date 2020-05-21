package com.kniziol.bank.domain.account;

import com.kniziol.bank.domain.value_object.Money;

import java.util.List;

public class Account {

    private Long id;
    private String firstName;
    private String lastName;
    private Money balance;
    private List<Money> history;

}
