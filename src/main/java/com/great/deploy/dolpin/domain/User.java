package com.great.deploy.dolpin.domain;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    private String nickname;
    private String favoriteId;
    private String activeRegion;
    private String medal;
    private String duckLevel;
    private String providerId;
    private String provider;

    public User(String nickname, String favoriteId, String activeRegion, String medal, String duckLevel, String providerId, String provider) {
        this.nickname = nickname;
        this.favoriteId = favoriteId;
        this.activeRegion = activeRegion;
        this.medal = medal;
        this.duckLevel = duckLevel;
        this.providerId = providerId;
        this.provider = provider;
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getFavoriteId() {
        return favoriteId;
    }

    public String getActiveRegion() {
        return activeRegion;
    }

    public String getMedal() {
        return medal;
    }

    public String getDuckLevel() {
        return duckLevel;
    }

    public String getProviderId() {
        return providerId;
    }

    public String getProvider() {
        return provider;
    }
}
