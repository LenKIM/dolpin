package com.great.deploy.dolpin.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "favorite")
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Embedded
    private Celebrites celebrites;

    @ManyToOne(optional = false)
    @JoinColumn(name = "accounts_id")
    private Accounts accounts;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;

    public Favorite() {
    }

    public Favorite(Accounts accounts, Celebrites celebrites) {
        this.celebrites = celebrites;
        this.accounts = accounts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Celebrites getCelebrites() {
        return celebrites;
    }

    public void setCelebrites(Celebrites celebrites) {
        this.celebrites = celebrites;
    }

    public Accounts getAccounts() {
        return accounts;
    }

    public void setAccounts(Accounts accounts) {
        this.accounts = accounts;
    }
}