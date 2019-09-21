package com.great.deploy.dolpin.dto;

import com.great.deploy.dolpin.domain.Favorite;
import com.great.deploy.dolpin.model.Provider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccessTokenResponse {

    private Integer accountId;
    private String email;
    private String name;
    private String imageUrl;
    private String nickName;
    private String activeRegion;
    private String medal;
    private String duckLevel;
    private Provider SNSType;
    private String SNSId;
    private Set<Favorite> favorites;
    private String accessToken;

    public static AccessTokenResponse of(AccessToken account) {
        return new AccessTokenResponse(
                account.getId(),
                account.getEmail(),
                account.getName(),
                account.getImageUrl(),
                account.getNickname(),
                account.getActiveRegion(),
                account.getMedal(),
                account.getDuckLevel(),
                account.getSnsType(),
                account.getSnsId(),
                account.getFavorites(),
                account.getAccessToken());
    }
}
