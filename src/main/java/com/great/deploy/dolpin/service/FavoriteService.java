package com.great.deploy.dolpin.service;

import com.great.deploy.dolpin.model.Accounts;
import com.great.deploy.dolpin.model.Celebrites;
import com.great.deploy.dolpin.model.Favorite;
import com.great.deploy.dolpin.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {

    @Autowired
    FavoriteRepository favoriteRepository;

    @Autowired
    AccountsService accountsService;

    public List<Favorite> saveCelebriteFavorites(Long userId, Celebrites celebrites){
        Accounts account = accountsService.getAccountByUserId(userId);
        favoriteRepository.save(new Favorite(account, celebrites));
        return accountsService.getAccounts(userId).getFavorite();

    }

    public List<Favorite> getFavorites(Long userId){
        return accountsService.getAccounts(userId).getFavorite();
    }
}
