package com.great.deploy.dolpin.dto;

import com.great.deploy.dolpin.dto.model.CelebrityType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DolpinRequest {

    @NotEmpty
    private Long celebrityId;
    @NotEmpty
    private CelebrityType celebrityType;
    @NotNull
    private String address;
    @NotNull
    private String detailedAddress;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
}