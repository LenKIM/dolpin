package com.great.deploy.dolpin.dto;

import com.great.deploy.dolpin.dto.model.CelebrityType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DolpinRequest {

    private Long celebrityId;
    private CelebrityType celebrityType;
    private String address;
    private String detailedAddress;
    private LocalDate startDate;
    private LocalDate endDate;
}