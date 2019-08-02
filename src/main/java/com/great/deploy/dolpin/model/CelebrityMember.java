package com.great.deploy.dolpin.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "celebrity_member")
public class CelebrityMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String name;
    private LocalDate birthday;
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
