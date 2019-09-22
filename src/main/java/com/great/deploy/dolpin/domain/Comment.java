package com.great.deploy.dolpin.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Pins.class)
    @JoinColumn(name = "pins_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Pins pins;

    private String contents;
    private Long accountId;
    private String nickName;
    private int recommendCount;

    @Transient
    private boolean recommended;

    public void increaseRecommendCount() {
        recommendCount++;
    }

    public void decreaseRecommendCount() {
        if (recommendCount > 0) {
            recommendCount--;
        }
    }

    @CreationTimestamp
    private LocalDateTime createAt;
    @UpdateTimestamp
    private LocalDateTime updateAt;

    public Comment(Pins pins, String contents, Long accountId, String nickName) {
        this.pins = pins;
        this.contents = contents;
        this.accountId = accountId;
        this.nickName = nickName;
    }
}
