package com.great.deploy.dolpin.model;

import javax.persistence.*;

@Entity
@Table(name = "history")
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String userName;
    private String celebrity;

    private Behavior behavior;

    public History(String userName, String celebrity) {
        this.userName = userName;
        this.celebrity = celebrity;
    }

    public Behavior getBehavior() {
        return behavior;
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getCelebrity() {
        return celebrity;
    }
}
