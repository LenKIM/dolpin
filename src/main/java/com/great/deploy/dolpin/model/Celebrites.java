package com.great.deploy.dolpin.model;

import javax.persistence.Embeddable;

@Embeddable
public class Celebrites {

    private int memberId;
    private int groupId;

    public Celebrites() {
    }

    public Celebrites(int memberId, int groupId) {
        this.memberId = memberId;
        this.groupId = groupId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}
