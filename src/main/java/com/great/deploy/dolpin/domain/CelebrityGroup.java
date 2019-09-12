package com.great.deploy.dolpin.domain;


import com.great.deploy.dolpin.common.AuditEntity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "celebrity_group")
public class CelebrityGroup extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate birthday;
    private String name;
    private String picUrl;

    public CelebrityGroup() {
    }

    public CelebrityGroup(String name, LocalDate birthday, String picUrl) {
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
