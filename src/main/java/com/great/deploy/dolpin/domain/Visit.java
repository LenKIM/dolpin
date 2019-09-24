package com.great.deploy.dolpin.domain;

import com.great.deploy.dolpin.common.AuditEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "visit")
public class Visit extends AuditEntity {

    public static final Visit NOT_FOUND = new Visit(null, null);

    public static Visit of(Long pinId, Integer accountId) {
        return new Visit(pinId, accountId);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long pinId;
    private Integer accountId;

    public Visit(Long pinId, Integer accountId) {
        this.pinId = pinId;
        this.accountId = accountId;
    }
}
