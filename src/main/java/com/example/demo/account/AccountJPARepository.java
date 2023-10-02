package com.example.demo.account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountJPARepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByEmail(String email);
}
