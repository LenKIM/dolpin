package com.great.deploy.dolpin.repository;

import com.great.deploy.dolpin.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    Account findByEmail(String email);
    Account findByOauthId(String oauthId);

    Boolean existsByEmail(String email);
    Boolean existsByOauthId(String oauthId);
    Boolean existsByNickname(String nickName);

}
