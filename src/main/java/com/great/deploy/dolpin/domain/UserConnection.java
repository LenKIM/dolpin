package com.great.deploy.dolpin.domain;

import javax.persistence.*;

@Entity
@Table(name = "user_connection")
public class UserConnection {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    private String accessToken;
    private String displayName;
    private String email;
    private String expireTime;
    private String imageUrl;
    private String profileUrl;
    private String provider;
    private String providerId;

    public UserConnection(String accessToken, String displayName, String email, String expireTime, String imageUrl, String profileUrl, String provider, String providerId) {
        this.accessToken = accessToken;
        this.displayName = displayName;
        this.email = email;
        this.expireTime = expireTime;
        this.imageUrl = imageUrl;
        this.profileUrl = profileUrl;
        this.provider = provider;
        this.providerId = providerId;
    }

    public Long getId() {
        return id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public String getProvider() {
        return provider;
    }

    public String getProviderId() {
        return providerId;
    }
}
