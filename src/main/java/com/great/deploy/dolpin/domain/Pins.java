package com.great.deploy.dolpin.domain;

import com.great.deploy.dolpin.common.AuditEntity;
import com.great.deploy.dolpin.dto.CreatePinRequest;
import lombok.*;

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
    private LocalDate startDate;
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

    public Long getCelebrityMemberId() {
        return celebrityMember.getId();
    }

    public Long getCelebrityGroupId() {
        return celebrityGroup.getId();
    }
}