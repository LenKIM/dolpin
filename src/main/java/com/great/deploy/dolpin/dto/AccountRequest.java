package com.great.deploy.dolpin.dto;

import io.swagger.annotations.ApiParam;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequest {

    private String nickname;

    @ApiParam(value = "Where you live")
    private String activeRegion;

    @ApiParam(value = "What you have")
    private String medal;

    private String duckLevel;


}
