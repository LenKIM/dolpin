package com.great.deploy.dolpin.model;

import javax.persistence.*;

@Entity
@Table(name = "visit")
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private Long pinId;

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
