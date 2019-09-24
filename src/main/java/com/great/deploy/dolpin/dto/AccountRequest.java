package com.great.deploy.dolpin.dto;

import com.great.deploy.dolpin.domain.Favorite;
import com.great.deploy.dolpin.dto.model.Provider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequest {

    private String email;
    private String nickname;
    private Provider snsType;
    private String snsId;
    private Set<Favorite> favorites;
}
