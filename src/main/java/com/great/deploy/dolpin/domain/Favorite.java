package com.great.deploy.dolpin.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter @NoArgsConstructor @AllArgsConstructor
public class Favorite {

    private Long memberId;
    private Long groupId;

}