//package com.great.deploy.dolpin.service;
//
//import com.great.deploy.dolpin.account.Account;
//import com.great.deploy.dolpin.account.CurrentUser;
//import com.great.deploy.dolpin.model.Celebrites;
//import com.great.deploy.dolpin.model.Favorite;
//import com.great.deploy.dolpin.repository.FavoriteRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class FavoriteService {
//
//    @Autowired
//    FavoriteRepository favoriteRepository;
//
//    public List<Favorite> saveCelebriteFavorites(@CurrentUser Account account, List<Celebrites> celebrites) {
//        return celebrites.stream()
//                .map(celebrite -> favoriteRepository.saveAndFlush(new Favorite(account, celebrite)))
//                .collect(Collectors.toList());
//    }
//
//    public List<Favorite> getFavoritesByAccount(Account account){
//        return account.getFavorite();
//    }
//}
