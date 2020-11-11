package com.example.demo.services;

import com.example.demo.models.Account;
import com.example.demo.models.User;

import java.math.BigDecimal;

public interface AccountService {
    Account createUserAccount(User user, Account account);
    void depositMoney(BigDecimal amount, Long accountId);
    void withdrawMoney(BigDecimal amount, Long accountId);
    void transferMoney(BigDecimal amount, Long fromId, Long toId);
}
