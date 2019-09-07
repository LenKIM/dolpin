package com.great.deploy.dolpin.model;

import com.great.deploy.dolpin.dto.CreatePinRequest;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pins")
public class Pins {

    public static Pins of(CreatePinRequest pinRequest, CelebrityMember celebrityMember, CelebrityGroup celebrityGroup) {
        return new Pins(
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

    public Pins(Double latitude, Double longitude, String title, String imgUrl, String imgProvider, LocalDate startDate, LocalDate endDate, CelebrityMember celebrityMember, CelebrityGroup celebrityGroup) {
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

}