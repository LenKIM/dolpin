package com.great.deploy.dolpin.dto;

import com.great.deploy.dolpin.domain.Favorite;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class FavoriteRequest {

    Set<Favorite> favorites;
}
