package com.example.demo.repositories;

import com.example.demo.entities.Account;
import com.example.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
