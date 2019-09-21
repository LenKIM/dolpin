package com.great.deploy.dolpin.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePinRequest {

    public final static CreatePinRequest EMPTY = new CreatePinRequest();

    private Long memberId;
    private Long groupId;
    private String title;
    private String imgUrl;
    private String imgProvider;
    private Double latitude;
    private Double longitude;
    private LocalDate startDate;
    private LocalDate endDate;
    private String address;
    private String detailedAddress;

}
