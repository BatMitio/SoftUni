package com.example.demo.services;

import com.example.demo.models.Account;
import com.example.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class Controller implements CommandLineRunner {
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;

    @Override
    public void run(String... args) throws Exception {
        User user = new User("Pesho", 19);
        userService.register(user);
        Account account = new Account(new BigDecimal(250000));
        accountService.createUserAccount(user, account);
    }
}
