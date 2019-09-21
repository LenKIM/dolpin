package com.great.deploy.dolpin.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PositingPeriod {

    LocalDate startDate;
    LocalDate endDate;


}
