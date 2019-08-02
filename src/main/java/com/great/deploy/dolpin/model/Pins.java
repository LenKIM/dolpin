package com.great.deploy.dolpin.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "pins")
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
            String imgProvider, LocalDate startDate, LocalDate endDate, LocalDateTime createdAt,
            LocalDateTime updateAt, CelebrityMember celebrityMember,
            CelebrityGroup celebrityGroup) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.title = title;
        this.imgUrl = imgUrl;
        this.imgProvider = imgProvider;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
        this.celebrityMember = celebrityMember;
        this.celebrityGroup = celebrityGroup;
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

        @CreationTimestamp
        private LocalDateTime createdAt;

        private CelebrityMember celebrityMember;
        private CelebrityGroup celebrityGroup;

        @UpdateTimestamp
        private LocalDateTime updatedAt;

        public PinsBuilder latitude(Double longi) {
            latitude = longi;
            return this;
        }

        public PinsBuilder longitude(Double longi) {
            longitude = longi;
            return this;
        }

        public PinsBuilder title(String str) {
            title = str;
            return this;
        }

        public PinsBuilder imgUrl(String str) {
            imgUrl = str;
            return this;
        }

        public PinsBuilder imgProvider(String str) {
            imgProvider = str;
            return this;
        }

        public PinsBuilder startDate(LocalDate localDate) {
            startDate = localDate;
            return this;
        }

        public PinsBuilder endDate(LocalDate localDate) {
            endDate = localDate;
            return this;
        }

        public PinsBuilder createdAt(LocalDateTime localDateTime) {
            createdAt = localDateTime;
            return this;
        }

        public PinsBuilder updatedAt(LocalDateTime localDateTime) {
            updatedAt = localDateTime;
            return this;
        }

        public PinsBuilder celebrityMember(CelebrityMember member) {
            celebrityMember = member;
            return this;
        }

        public PinsBuilder celebrityGroup(CelebrityGroup group) {
            celebrityGroup = group;
            return this;
        }

        public Pins build() {
            Pins pins = new Pins(latitude, longitude, title, imgUrl, imgProvider, startDate,
                    endDate, createdAt,
                    updatedAt, celebrityMember, celebrityGroup);
            return pins;
        }


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
