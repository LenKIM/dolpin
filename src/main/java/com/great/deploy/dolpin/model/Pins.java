package com.great.deploy.dolpin.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "pins")
@EntityListeners(AuditingEntityListener.class)
public class Pins {

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }


    public static class PinsBuilder {

        //Required parameters
        private Long id;
        private Double latitude;
        private Double longitude;
        private String title;
        private String imgUrl;
        private String imgProvider;
        private LocalDate startDate;
        private LocalDate endDate;

        private CelebrityMember celebrityMember;
        private CelebrityGroup celebrityGroup;

        @CreationTimestamp
        private LocalDateTime createdAt;

        @UpdateTimestamp
        private LocalDateTime updatedAt;

        @CreatedBy
        private String createdBy;

        @LastModifiedBy
        private String modifiedBy;

        public PinsBuilder latitude(Double longi) {
            latitude = longi;
            return this;
        }

    public Double getLongitude() {
        return longitude;
    }


    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgProvider() {
        return imgProvider;
    }

    public void setImgProvider(String imgProvider) {
        this.imgProvider = imgProvider;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public CelebrityMember getCelebrityMember() {
        return celebrityMember;
    }

    public void setCelebrityMember(CelebrityMember celebrityMember) {
        this.celebrityMember = celebrityMember;
    }

    public CelebrityGroup getCelebrityGroup() {
        return celebrityGroup;
    }

    public void setCelebrityGroup(CelebrityGroup celebrityGroup) {
        this.celebrityGroup = celebrityGroup;
    }

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;

    @ManyToOne
    @JoinColumn(name = "celebrity_member_id")
    private CelebrityMember celebrityMember;

    @ManyToOne
    @JoinColumn(name = "celebrity_group_id")
    private CelebrityGroup celebrityGroup;

    public Pins() {
    }

    public Pins(Double latitude, Double longitude, String title, String imgUrl,
            String imgProvider, LocalDate startDate, LocalDate endDate, CelebrityMember celebrityMember,
            CelebrityGroup celebrityGroup) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.title = title;
        this.imgUrl = imgUrl;
        this.imgProvider = imgProvider;
        this.startDate = startDate;
        this.endDate = endDate;
        this.celebrityMember = celebrityMember;
        this.celebrityGroup = celebrityGroup;
    }


}
