package com.great.deploy.dolpin.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.great.deploy.dolpin.common.AuditEntity;
import com.great.deploy.dolpin.dto.CreatePinRequest;
import com.great.deploy.dolpin.dto.PinRequest;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pins")
public class Pins extends AuditEntity {

    public static Pins of(CreatePinRequest pinRequest, CelebrityMember celebrityMember, CelebrityGroup celebrityGroup, String address, String detailedAddress) {
        return new Pins(
                pinRequest.getAddress(),
                pinRequest.getDetailedAddress(),
                pinRequest.getLatitude(),
                pinRequest.getLongitude(),
                pinRequest.getTitle(),
                pinRequest.getImgUrl(),
                pinRequest.getImgProvider(),
                pinRequest.getStartDate(),
                pinRequest.getEndDate(),
                celebrityMember,
                celebrityGroup);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double latitude;
    private Double longitude;
    private String title;
    private String imgUrl;
    private String imgProvider;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate endDate;

    private String address;
    private String detailedAddress;

    @ManyToOne
    @JoinColumn(name = "celebrity_member_id")
    private CelebrityMember celebrityMember;

    @ManyToOne
    @JoinColumn(name = "celebrity_group_id")
    private CelebrityGroup celebrityGroup;

    public Pins(String address, String detailedAddress, Double latitude, Double longitude, String title, String imgUrl, String imgProvider,
                LocalDate startDate, LocalDate endDate, CelebrityMember celebrityMember, CelebrityGroup celebrityGroup) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.title = title;
        this.imgUrl = imgUrl;
        this.imgProvider = imgProvider;
        this.startDate = startDate;
        this.endDate = endDate;
        this.celebrityMember = celebrityMember;
        this.celebrityGroup = celebrityGroup;
        this.address = address;
        this.detailedAddress = detailedAddress;
    }

    public Pins(Pins pins, String imageUrl, CelebrityMember celebrityMember, CelebrityGroup celebrityGroup) {
        new Pins(pins.getAddress(),
                pins.getDetailedAddress(),
                pins.getLatitude(),
                pins.getLongitude(),
                pins.getTitle(),
                imageUrl,
                pins.getImgProvider(),
                pins.getStartDate(),
                pins.getEndDate(),
                celebrityMember,
                celebrityGroup);
    }

    public Long getCelebrityMemberId() {
        return celebrityMember.getId();
    }

    public Long getCelebrityGroupId() {
        return celebrityGroup.getId();
    }

    public void setDate(PinRequest pinRequest) {
        this.setTitle(pinRequest.getTitle());
        this.setImgUrl(pinRequest.getImgUrl());
        this.setImgProvider(pinRequest.getImgProvider());
        this.setLatitude(pinRequest.getLatitude());
        this.setLongitude(pinRequest.getLongitude());
        this.setStartDate(pinRequest.getStartDate());
        this.setEndDate(pinRequest.getEndDate());
        this.setAddress(pinRequest.getAddress());
        this.setDetailedAddress(pinRequest.getDetailedAddress());
    }
}