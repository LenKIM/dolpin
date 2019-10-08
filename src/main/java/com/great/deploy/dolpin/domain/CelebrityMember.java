package com.great.deploy.dolpin.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@ToString
@Entity
@Table(name = "celebrity_member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CelebrityMember{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate birthday;
    private String name;
    private String picUrl;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "group_id")
    private CelebrityGroup celebrityGroup;

    public CelebrityMember(String name, LocalDate birthday, String picUrl) {
        this.name = name;
        this.birthday = birthday;
        this.picUrl = picUrl;
    }
}
