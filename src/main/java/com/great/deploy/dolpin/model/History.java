package com.great.deploy.dolpin.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "history")
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String userName;
    private String celebrity;
    private Behavior behavior;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;

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
