package com.great.deploy.dolpin.dto;

import com.great.deploy.dolpin.account.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AccessToken extends Account {

    String accessToken;
    String tokenType;
    String refreshToken;
    Integer expiresIn;
    String scope;

    public AccessToken(Account account, String accessToken, String tokenType, String refreshToken, Integer expiresIn, String scope) {
        super(account.getId(), account.getPassword(), account.getEmail(), account.getRoles(), account.getName(), account.getImageUrl(), account.getNickname(), account.getActiveRegion(), account.getMedal(), account.getDuckLevel(), account.getType(), account.getFavorite(), account.getCreatedAt(), LocalDateTime.now());
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
        this.scope = scope;
    }
}


//{
//        "access_token": "e377ab2b-1909-4c72-a2e6-a5ff88cc0313",
//        "token_type": "bearer",
//        "refresh_token": "a802051b-fd7a-40ea-862b-b81e555775d7",
//        "expires_in": 7199,
//        "scope": "read write"
//        }