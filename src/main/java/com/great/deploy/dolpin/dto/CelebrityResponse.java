package com.great.deploy.dolpin.dto;

import java.time.LocalDate;

public class CelebrityResponse {

    private Long id;
    private String name;
    private LocalDate birthday;
    private String picUrl;
    private Long groupId;
    private String groupName;

    public CelebrityResponse() {
    }

    public CelebrityResponse(Long id, String name, LocalDate birthday, String picUrl, Long groupId, String groupName) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.picUrl = picUrl;
        this.groupId = groupId;
        this.groupName = groupName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
