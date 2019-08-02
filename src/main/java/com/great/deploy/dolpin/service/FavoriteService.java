package com.great.deploy.dolpin.service;

import com.great.deploy.dolpin.model.Accounts;
import com.great.deploy.dolpin.model.Celebrites;
import com.great.deploy.dolpin.model.Favorite;
import com.great.deploy.dolpin.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteService {

    @Autowired
    FavoriteRepository favoriteRepository;

    @Autowired
    AccountsService accountsService;

    public List<Favorite> saveCelebriteFavorites(Long userId, List<Celebrites> celebrites) {
        Accounts account = accountsService.getAccountByUserId(userId);
        return celebrites.stream()
                .map(celebrite -> favoriteRepository.saveAndFlush(new Favorite(account, celebrite)))
                .collect(Collectors.toList());
    }

    public List<Favorite> getFavorites(Long userId) {
        return accountsService.getAccounts(userId).getFavorite();
    }
}
