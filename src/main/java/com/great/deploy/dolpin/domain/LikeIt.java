package com.great.deploy.dolpin.domain;

import com.great.deploy.dolpin.account.Account;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
