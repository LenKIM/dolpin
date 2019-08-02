package com.great.deploy.dolpin.dto;

import com.great.deploy.dolpin.model.Celebrites;

import java.util.List;

public class FavoriteResponse {


    List<Celebrites> celebrites;

    public FavoriteResponse() {
    }

    public FavoriteResponse(List<Celebrites> celebrites) {
        this.celebrites = celebrites;
    }

    public List<Celebrites> getCelebrites() {
        return celebrites;
    }
}