package com.great.deploy.dolpin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {

    private Long id;
    private Long accountId;
    private String contents;
    private String nickName;
    private int likeItCount;
    private boolean recommended;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}
