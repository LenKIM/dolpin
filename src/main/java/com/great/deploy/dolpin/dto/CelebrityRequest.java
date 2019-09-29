package com.great.deploy.dolpin.dto;

import com.great.deploy.dolpin.dto.model.CelebrityType;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CelebrityRequest {
    @NotEmpty
    private Long celebrityId;
    @NotEmpty
    private CelebrityType celebrityType;

}
