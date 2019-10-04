package com.great.deploy.dolpin.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.great.deploy.dolpin.common.AuditEntity;
import com.great.deploy.dolpin.dto.model.DolpinType;
import com.great.deploy.dolpin.dto.model.PositingPeriod;
import com.great.deploy.dolpin.dto.model.PostedAddress;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "dolpin")
public class Dolpin extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long celebrityMemberId;
    private String address;
    private String detailedAddress;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
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