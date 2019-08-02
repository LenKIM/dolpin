package com.great.deploy.dolpin.model;

import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "pins")
public class Pins {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private Double latitude;
    private Double longitude;
    private Category category;
    private String title;
    private String imgUrl;
    private String imgProvider;
    private LocalDate startDate;
    private LocalDate endDate;

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

    public Pins(Double latitude, Double longitude, String title, String imgUrl, String imgProvider, LocalDate startDate, LocalDate endDate) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.title = title;
        this.imgUrl = imgUrl;
        this.imgProvider = imgProvider;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getTitle() {
        return title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getImgProvider() {
        return imgProvider;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
