package com.great.deploy.dolpin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class DolpinRequest {

    private String title;
    private String imgUrl;
    private String imgProvider;
    private String latitude;
    private String longitude;

}