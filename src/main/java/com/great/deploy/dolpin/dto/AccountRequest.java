package com.great.deploy.dolpin.dto;

import com.great.deploy.dolpin.domain.Favorite;
import com.great.deploy.dolpin.dto.model.Provider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequest {

    @NotNull
    private String email;
    @NotNull
    private String nickname;
    @NotNull
    private Provider snsType;
    @NotNull
    private String snsId;
    private Set<Favorite> favorites;
}
