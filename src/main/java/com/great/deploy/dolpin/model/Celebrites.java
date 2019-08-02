package com.great.deploy.dolpin.model;

import javax.persistence.Embeddable;

@Embeddable
public class Celebrites {

    private Long memberId;
    private Long groupId;

    public Celebrites() {
    }

    public Celebrites(Long memberId, Long groupId) {
        this.memberId = memberId;
        this.groupId = groupId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
