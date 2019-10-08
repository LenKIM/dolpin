package com.great.deploy.dolpin.domain;

import com.great.deploy.dolpin.common.AuditEntity;
import com.great.deploy.dolpin.dto.model.DolpinType;
import com.great.deploy.dolpin.dto.model.PositingPeriod;
import com.great.deploy.dolpin.dto.model.PostedAddress;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "dolpin")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dolpin extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long celebrityMemberId;
    private String address;
    private String detailedAddress;
    private LocalDate startDate;
    private LocalDate endDate;
    private String imgUrl;

    @Enumerated(EnumType.STRING)
    private DolpinType dolpinType;

    public Dolpin(Long celebrityMemberId, PostedAddress address, PositingPeriod period, String imgUrl, DolpinType dolpinType) {
        this.celebrityMemberId = celebrityMemberId;
        this.address = address.getAddress();
        this.detailedAddress = address.getDetailedAddress();
        this.startDate = period.getStartDate();
        this.endDate = period.getEndDate();
        this.imgUrl = imgUrl;
        this.dolpinType = dolpinType;
    }
}