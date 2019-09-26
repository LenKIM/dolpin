package com.great.deploy.dolpin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {

    public CommentRequest(Integer accountId, String contents) {
        this.accountId = accountId;
        this.contents = contents;
    }
    @NotNull
    private Integer accountId;
    @NotNull
    private String contents;
    @NotNull
    private String nickName;
}
