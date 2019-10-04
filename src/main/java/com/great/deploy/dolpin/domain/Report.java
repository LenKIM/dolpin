package com.great.deploy.dolpin.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "report")
@Getter
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
