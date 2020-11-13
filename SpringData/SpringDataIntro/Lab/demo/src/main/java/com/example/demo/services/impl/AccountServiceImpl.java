package com.example.demo.services.impl;

import com.example.demo.exeption.InvalidAccountOperationException;
import com.example.demo.exeption.NonexistingEntityException;
import com.example.demo.models.Account;
import com.example.demo.models.User;
import com.example.demo.repositories.AccountRepository;
import com.example.demo.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Transactional
@Service
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepo;

    @Autowired
    public void setAccountRepo(AccountRepository accountRepo) {
        this.accountRepo = accountRepo;

    }

    @Override
    public Account createUserAccount(User user, Account account) {
        account.setId(null);
        account.setUser(user);
        user.getAccounts().add(account);
        return accountRepo.save(account);
    }

    @Override
    public void depositMoney(BigDecimal amount, Long accountId) {
        Account account = accountRepo.findById(accountId).orElseThrow(() ->
                new NonexistingEntityException(String.format("Entity with ID: %s does not exist!", accountId)));
        if(amount.compareTo(new BigDecimal(0)) < 0)
            throw new InvalidAccountOperationException("Deposit amount cannot be less than zero!");

        account.setBalance(account.getBalance().add(amount));
    }

    @Override
    public void withdrawMoney(BigDecimal amount, Long accountId) {
        Account account = accountRepo.findById(accountId).orElseThrow(() ->
                new NonexistingEntityException(String.format("Entity with ID: %s does not exist!")));

        if(account.getBalance().compareTo(amount) < 0)
            throw new InvalidAccountOperationException(String.format("Account ID: %s insufficient balance!", accountId));

        account.setBalance(account.getBalance().subtract(amount));
    }

    @Override
    public void transferMoney(BigDecimal amount, Long fromAccountId, Long toAccountId) {
        withdrawMoney(amount, fromAccountId);
        depositMoney(amount, toAccountId);
    }
}
