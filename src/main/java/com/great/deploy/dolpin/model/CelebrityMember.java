package com.great.deploy.dolpin.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "celebrity_member")
public class CelebrityMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String name;
    private LocalDate birthday;
    private String picUrl;


    public CelebrityMember(String name, LocalDate birthday, String picUrl) {
        this.name = name;
        this.birthday = birthday;
        this.picUrl = picUrl;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public String getPicUrl() {
        return picUrl;
    }
}
