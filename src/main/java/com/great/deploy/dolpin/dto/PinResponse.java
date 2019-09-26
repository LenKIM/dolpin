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
    private Long likeCount;

    public PinResponse(Pins pin, boolean isVisited, Long likeCount) {
        this.id = pin.getId();
        this.title = pin.getTitle();
        this.latitude = pin.getLatitude();
        this.longitude = pin.getLongitude();
        this.imgProvider = pin.getImgProvider();
        this.imgUrl = pin.getImgUrl();
        this.startDate = pin.getStartDate();
        this.endDate = pin.getEndDate();
        this.member_id = pin.getCelebrityMemberId();
        this.group_id = pin.getCelebrityGroupId();
        this.isPinCertification = isVisited;
        this.address = pin.getAddress();
        this.detailedAddress = pin.getDetailedAddress();
        this.likeCount = likeCount;
    }
}
