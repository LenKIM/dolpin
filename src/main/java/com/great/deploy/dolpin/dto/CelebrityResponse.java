package com.great.deploy.dolpin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CelebrityResponse {

    private Long id;
    private String name;
    private LocalDate birthday;
    private String picUrl;
    private Long groupId;
    private String groupName;

}