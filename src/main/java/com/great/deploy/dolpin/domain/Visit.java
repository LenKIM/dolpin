package com.great.deploy.dolpin.domain;

import com.great.deploy.dolpin.common.AuditEntity;

import javax.persistence.*;

@Entity
@Table(name = "visit")
public class Visit extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private Long pinId;

    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public Visit(Long pinId) {
        this.pinId = pinId;
    }

    public Long getId() {
        return id;
    }

    public Long getPinId() {
        return pinId;
    }
}
