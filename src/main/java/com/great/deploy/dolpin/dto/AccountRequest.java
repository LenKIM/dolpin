package com.great.deploy.dolpin.dto;

import com.great.deploy.dolpin.domain.Favorite;
import com.great.deploy.dolpin.model.Provider;
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

    String email;
    String nickname;
    Provider type;
    Set<Favorite> favorites;
}
