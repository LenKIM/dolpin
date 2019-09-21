package com.great.deploy.dolpin.dto;

import com.great.deploy.dolpin.domain.Favorite;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountSigupResponse {

    private Integer id;
    private String email;
    private String name;
    private String imageUrl;
    private String nickname;
    private String activeRegion;
    private String medal;
    private String duckLevel;
    private Set<Favorite> favorite = new HashSet<>();
    private String accessToken;
}
