package com.great.deploy.dolpin.dto;

import com.great.deploy.dolpin.model.Favorite;

import java.util.Set;

public class FavoriteResponse {

    private Set<Favorite> celebrites;

    public FavoriteResponse() {
    }

    public FavoriteResponse(Set<Favorite> celebrites) {
        this.celebrites = celebrites;
    }

    public Set<Favorite> getCelebrites() {
        return celebrites;
    }

    public void setCelebrites(Set<Favorite> celebrites) {
        this.celebrites = celebrites;
    }
}
