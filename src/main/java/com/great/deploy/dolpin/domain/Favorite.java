package com.great.deploy.dolpin.domain;


import com.great.deploy.dolpin.common.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Favorite extends AuditEntity {

    private Long memberId;
    private Long groupId;

}