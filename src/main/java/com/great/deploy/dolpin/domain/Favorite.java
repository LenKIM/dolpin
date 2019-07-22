package com.great.deploy.dolpin.domain;

import javax.persistence.*;

@Entity
@Table(name = "favorite")
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    private Long userId;
    private Long memberId;
    private Long groupId;

    public Favorite(Long userId, Long memberId, Long groupId) {
        this.userId = userId;
        this.memberId = memberId;
        this.groupId = groupId;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getGroupId() {
        return groupId;
    }
}
