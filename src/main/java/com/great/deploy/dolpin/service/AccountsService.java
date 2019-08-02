package com.great.deploy.dolpin.service;

import com.great.deploy.dolpin.dto.AccountRequest;
import com.great.deploy.dolpin.exception.ResourceNotFoundException;
import com.great.deploy.dolpin.model.Accounts;
import com.great.deploy.dolpin.repository.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountsService {

    @Autowired
    AccountsRepository accountsRepository;

    public Accounts getAccounts(Long userId) {
        if (!accountsRepository.existsById(userId)) {
            throw new UsernameNotFoundException("Account not found with id " + userId);
        }
        return accountsRepository
                .findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Account not found with userId : " + userId));
    }


    public Accounts updateAccounts(Long userId, AccountRequest accountRequest) {

        if (!accountsRepository.existsById(userId)) {
            throw new UsernameNotFoundException("Account not found with id " + userId);
        }

        return accountsRepository.findById(userId).map(
                user -> {
                    user.setNickname(accountRequest.getNickname());
                    user.setActiveRegion(accountRequest.getActiveRegion());
                    user.setMedal(accountRequest.getMedal());
                    user.setDuckLevel(accountRequest.getDuckLevel());
                    return accountsRepository.save(user);
                }).orElseThrow(() -> new ResourceNotFoundException("Users", "id", userId));
    }

    public Boolean deleteAccounts(Long userId) {
        return accountsRepository.findById(userId)
                .map(account -> {
                    accountsRepository.delete(account);
                    return true;
                }).orElseThrow(() -> new UsernameNotFoundException("Account not found with userId : " + userId));
    }

    Accounts getAccountByUserId(Long userId) {
        return accountsRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Users", "id", userId));
    }
}
