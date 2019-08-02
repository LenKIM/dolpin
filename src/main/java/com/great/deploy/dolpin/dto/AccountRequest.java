package com.great.deploy.dolpin.dto;

public class AccountRequest {

    private String nickname;

    private String activeRegion;

    private String medal;

    private String duckLevel;

    public AccountRequest() {
    }

    public AccountRequest(String nickname, String activeRegion, String medal, String duckLevel) {
        this.nickname = nickname;
        this.activeRegion = activeRegion;
        this.medal = medal;
        this.duckLevel = duckLevel;
    }

    public String getNickname() {
        return nickname;
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
}
