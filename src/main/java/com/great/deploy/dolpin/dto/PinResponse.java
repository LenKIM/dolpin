package com.great.deploy.dolpin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PinResponse {


    private Long id;
    private String title;
    private Double latitude;
    private Double longitude;
    private String imgProvider;
    private String imgUrl;
    private LocalDate startDate;
    private LocalDate endDate;
}
