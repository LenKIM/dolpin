package com.great.deploy.dolpin.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "report")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String celebrityGroupName;
    String memberNameList;

    public Report(String celebrityGroupName, String memberNameList) {
        this.celebrityGroupName = celebrityGroupName;
        this.memberNameList = memberNameList;
    }
}
