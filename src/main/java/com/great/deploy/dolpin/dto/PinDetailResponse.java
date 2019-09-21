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
public class PinDetailResponse {

    private Long id;
    private String title;
    private String imgUrl;
    private String imgProvider;
    private LocalDate startDate;
    private LocalDate endDate;

}
