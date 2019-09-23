package com.great.deploy.dolpin.dto;

import com.great.deploy.dolpin.domain.Pins;
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
    private Long member_id;
    private Long group_id;
    private boolean isPinCertification;
    private String address;
    private String detailedAddress;

    public PinResponse(Pins pin, boolean isVisited) {
        new PinResponse(
                pin.getId(),
                pin.getTitle(),
                pin.getLatitude(),
                pin.getLongitude(),
                pin.getImgProvider(),
                pin.getImgUrl(),
                pin.getStartDate(),
                pin.getEndDate(),
                pin.getCelebrityMemberId(),
                pin.getCelebrityGroupId(),
                isVisited,
                pin.getAddress(),
                pin.getDetailedAddress());
    }
}
