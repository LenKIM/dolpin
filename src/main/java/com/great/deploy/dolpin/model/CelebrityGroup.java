package com.great.deploy.dolpin.model;


import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "celebrity_group")
public class CelebrityGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private LocalDate birthday;
    private String picUrl;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;

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
