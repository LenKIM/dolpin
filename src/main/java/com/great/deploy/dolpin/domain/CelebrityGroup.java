package com.great.deploy.dolpin.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "celebrity_group")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CelebrityGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate birthday;
    private String name;
    private String picUrl;

    public CelebrityGroup(String name, LocalDate birthday, String picUrl) {
        this.name = name;
        this.birthday = birthday;
        this.picUrl = picUrl;
    }
}
