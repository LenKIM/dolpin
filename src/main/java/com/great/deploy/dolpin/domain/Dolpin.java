package com.great.deploy.dolpin.domain;

import com.great.deploy.dolpin.common.AuditEntity;
import com.great.deploy.dolpin.dto.model.CelebrityType;
import com.great.deploy.dolpin.dto.model.Celebrity;
import com.great.deploy.dolpin.dto.model.PositingPeriod;
import com.great.deploy.dolpin.dto.model.PostedAddress;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "dolpin")
public class Dolpin extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long celebrityId;
    private CelebrityType celebrityType;

    private String address;
    private String detailedAddress;

    private LocalDate startDate;
    private LocalDate endDate;

    public Dolpin(Celebrity celebrity, PostedAddress address, PositingPeriod period) {
        this.celebrityId = celebrity.getCelebrityId();
        this.celebrityType = celebrity.getCelebrityType();
        this.address = address.getAddress();
        this.detailedAddress = address.getDetailedAddress();
        this.startDate = period.getStartDate();
        this.endDate = period.getEndDate();
    }
}