package com.great.deploy.dolpin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {

    public CommentRequest(Integer accountId, String contents) {
        this.accountId = accountId;
        this.contents = contents;
    }

    private Integer accountId;
    private String contents;
    private String nickName;
}
