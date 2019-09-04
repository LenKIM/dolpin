package com.great.deploy.dolpin.repository;

import com.great.deploy.dolpin.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    Optional<Account> findByEmail(String email);

    Boolean existsByEmail(String email);
}
