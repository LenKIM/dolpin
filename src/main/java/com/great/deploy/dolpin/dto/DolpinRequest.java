package com.great.deploy.dolpin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class DolpinRequest {

    private Long celebrityId;
    private CelebrityType celebrityType;

    private String imgProvider;
    private String latitude;
    private String longitude;
//    아이돌 /  위치검색+상세위치 / 날짜 입력 /
}