package com.great.deploy.dolpin.dto;

import com.great.deploy.dolpin.dto.model.DolpinType;
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

    public static final DolpinRequest EMPTY = new DolpinRequest(null, "", "", null, null, DolpinType.none);

    @NotEmpty
    private Long celebrityMemberId;
    @NotNull
    private String address;
    @NotNull
    private String detailedAddress;
    private LocalDate startDate;
    private LocalDate endDate;
    private DolpinType type;
}