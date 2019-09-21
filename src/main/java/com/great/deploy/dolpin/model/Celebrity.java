package com.great.deploy.dolpin.model;

import com.great.deploy.dolpin.dto.CelebrityType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Celebrity {

    private Long celebrityId;
    private CelebrityType celebrityType;
}
