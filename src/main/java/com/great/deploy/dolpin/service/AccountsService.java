package com.great.deploy.dolpin.service;

import com.great.deploy.dolpin.dto.AccountRequest;
import com.great.deploy.dolpin.exception.ResourceNotFoundException;
import com.great.deploy.dolpin.model.Accounts;
import com.great.deploy.dolpin.model.Pins;
import com.great.deploy.dolpin.repository.AccountsRepository;
import com.great.deploy.dolpin.repository.PinsRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountsService {

    @Autowired
    AccountsRepository accountsRepository;

    @Autowired
    PinsRepository pinsRepository;

    public Accounts getAccounts(Long userId) {
        if (!accountsRepository.existsById(userId)) {
            throw new UsernameNotFoundException("Account not found with id " + userId);
        }
        return accountsRepository
                .findById(userId)
                .orElseThrow(
                        () -> new UsernameNotFoundException(
                                "Account not found with userId : " + userId));
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
                }).orElseThrow(
                        () -> new UsernameNotFoundException(
                                "Account not found with userId : " + userId));
    }

    public Accounts getAccountByUserId(Long userId) {
        return accountsRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Users", "id", userId));
    }


    public List<Pins> getPinsByUserId(Long userId) {
        List<Long> groupIdList = accountsRepository.findById(userId).get().getFavorite().stream()
                .map(favorite -> favorite.getCelebrites().getGroupId())
                .collect(Collectors.toList());
        List<Long> memberIdList = accountsRepository.findById(userId).get().getFavorite().stream()
                .map(favorite -> favorite.getCelebrites().getMemberId())
                .collect(Collectors.toList());
        List<Pins> byCelebrityMember_idIn = pinsRepository
                .findPinsByCelebrityGroup_IdInAndCelebrityMember_IdIn(groupIdList, memberIdList);
        return byCelebrityMember_idIn;
    }
}
