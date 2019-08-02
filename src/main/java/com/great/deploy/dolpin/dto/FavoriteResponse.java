package com.great.deploy.dolpin.dto;

import com.great.deploy.dolpin.model.Celebrites;

import java.util.List;

public class FavoriteResponse {

    private List<Celebrites> celebrites;

    public FavoriteResponse() {
    }

    public FavoriteResponse(List<Celebrites> celebrites) {
        this.celebrites = celebrites;
    }

    public List<Celebrites> getCelebrites() {
        return celebrites;
    }

    public void setCelebrites(List<Celebrites> celebrites) {
        this.celebrites = celebrites;
    }
}
