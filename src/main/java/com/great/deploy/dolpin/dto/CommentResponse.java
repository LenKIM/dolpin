package com.great.deploy.dolpin.dto;

import com.great.deploy.dolpin.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {

    public CommentResponse(Comment comment, boolean recommended) {
        this.id = comment.getId();
        this.accountId = comment.getAccountId();
        this.contents = comment.getContents();
        this.nickName = comment.getNickName();
        this.likeItCount = comment.getRecommendCount();
        this.recommended = recommended;
        this.createAt = comment.getCreateAt();
        this.updateAt = comment.getUpdateAt();
    }

    private Long id;
    private Integer accountId;
    private String contents;
    private String nickName;
    private int likeItCount;
    private boolean recommended;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}
