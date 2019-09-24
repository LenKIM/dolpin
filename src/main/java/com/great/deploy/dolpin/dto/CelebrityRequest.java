package com.great.deploy.dolpin.dto;

import com.great.deploy.dolpin.dto.model.CelebrityType;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CelebrityRequest {

    private Long celebrityId;
    private CelebrityType celebrityType;

}
