package com.great.deploy.dolpin.dto;

import com.great.deploy.dolpin.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PinResponse {


    private Long id;
    private String title;
    private Double latitude;
    private Double longitude;
    private String imgProvider;
    private String imgUrl;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long member_id;
    private Long group_id;
    private List<Comment> comments;
    private boolean isPinCertification;
}
