package com.great.deploy.dolpin.domain;

import com.great.deploy.dolpin.common.AuditEntity;
import com.great.deploy.dolpin.model.Behavior;

import javax.persistence.*;

@Entity
@Table(name = "history")
public class History extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private Long userId;
    private String celebrity;
    private Behavior behavior;

    public History(Long userId, String celebrity, Behavior behavior) {
        this.userId = userId;
        this.celebrity = celebrity;
        this.behavior = behavior;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCelebrity() {
        return celebrity;
    }

    public void setCelebrity(String celebrity) {
        this.celebrity = celebrity;
    }

    public Behavior getBehavior() {
        return behavior;
    }

    public void setBehavior(Behavior behavior) {
        this.behavior = behavior;
    }
}