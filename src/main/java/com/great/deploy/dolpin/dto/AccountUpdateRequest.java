package com.great.deploy.dolpin.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountUpdateRequest {

    private String nickname;
    private String activeRegion;
    private String medal;
    private String duckLevel;

}
