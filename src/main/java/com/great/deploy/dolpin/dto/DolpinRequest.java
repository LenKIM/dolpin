package com.great.deploy.dolpin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DolpinRequest {

    public static final DolpinRequest EMPTY = new DolpinRequest(null, "", "", null, null);

    @NotEmpty
    private Long celebrityMemberId;
    @NotNull
    private String address;
    @NotNull
    private String detailedAddress;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
}