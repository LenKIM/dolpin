package com.great.deploy.dolpin.repository;

import com.great.deploy.dolpin.model.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountsRepository extends JpaRepository<Accounts, Long> {
    Optional<Accounts> findByEmail(String email);

    Boolean existsByEmail(String email);
}
