package com.great.deploy.dolpin.domain;

import com.great.deploy.dolpin.account.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "like_it")
public class LikeIt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {
            CascadeType.MERGE,
            CascadeType.REFRESH
    })
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {
            CascadeType.MERGE,
            CascadeType.REFRESH
    })
    private Account account;

    public LikeIt(Comment comment, Account account) {
        this.comment = comment;
        this.account = account;
    }

    @PrePersist
    public void onPrePersistRecommend() {
        this.comment.increaseRecommendCount();
    }

    @PreRemove
    public void onPreRemoveRecommend() {
        this.comment.decreaseRecommendCount();
    }
}
