package com.great.deploy.dolpin.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.great.deploy.dolpin.common.AuditEntity;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "celebrity_member")
@NoArgsConstructor
public class CelebrityMember extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

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

    public CelebrityGroup getCelebrityGroup() {
        return celebrityGroup;
    }
}
