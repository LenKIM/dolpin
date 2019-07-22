package com.great.deploy.dolpin.domain;

import javax.persistence.*;

@Entity
@Table(name = "history")
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    private String userName;
    private String celebrity;

    public enum behavior {
        CERTIFICATION, REPORT, FIX
    }

    public History(String userName, String celebrity) {
        this.userName = userName;
        this.celebrity = celebrity;
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
