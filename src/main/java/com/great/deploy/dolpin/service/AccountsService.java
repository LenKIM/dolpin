package com.great.deploy.dolpin.service;

import com.great.deploy.dolpin.dto.AccountReqeust;
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


    public Accounts updateAccounts(Long userId, AccountReqeust accountReqeust) {

        if (!accountsRepository.existsById(userId)) {
            throw new UsernameNotFoundException("Account not found with id " + userId);
        }

        return accountsRepository.findById(userId).map(
                user -> {
                    user.setNickname(accountReqeust.getNickname());
                    user.setActiveRegion(accountReqeust.getActiveRegion());
                    user.setMedal(accountReqeust.getMedal());
                    user.setDuckLevel(accountReqeust.getDuckLevel());
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

    public Accounts getAccountByUserId(Long userId) {
        return accountsRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Users", "id", userId));
    }


}