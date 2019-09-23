package com.great.deploy.dolpin.dto;

import com.great.deploy.dolpin.account.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountWithToken extends Account {

    private String accessToken;
    private String tokenType;
    private String refreshToken;
    private Integer expiresIn;
    private String scope;

    public AccountWithToken(Account account, AccessToken token) {
        super(account.getId(), account.getPassword(), account.getEmail(), account.getRoles(), account.getName(), account.getImageUrl(), account.getNickname(), account.getActiveRegion(), account.getMedal(), account.getDuckLevel(), account.getSnsType(), account.getSnsId(), account.getOauthId(), account.getFavorites(), account.getCreatedAt(), LocalDateTime.now());
        this.accessToken = token.getAccessToken();
        this.tokenType = token.getTokenType();
        this.refreshToken = token.getRefreshToken();
        this.expiresIn = token.getExpiresIn();
        this.scope = token.getScope();
    }
}

//{
//        "access_token": "e377ab2b-1909-4c72-a2e6-a5ff88cc0313",
//        "token_type": "bearer",
//        "refresh_token": "a802051b-fd7a-40ea-862b-b81e555775d7",
//        "expires_in": 7199,
//        "scope": "read write"
//        }