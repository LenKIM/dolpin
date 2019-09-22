package com.great.deploy.dolpin.controller;

import com.great.deploy.dolpin.account.Account;
import com.great.deploy.dolpin.account.CurrentUser;
import com.great.deploy.dolpin.domain.Comment;
import com.great.deploy.dolpin.domain.LikeIt;
import com.great.deploy.dolpin.dto.LikeItRequest;
import com.great.deploy.dolpin.dto.LikeItResponse;
import com.great.deploy.dolpin.dto.Response;
import com.great.deploy.dolpin.exception.BadRequestException;
import com.great.deploy.dolpin.repository.AccountRepository;
import com.great.deploy.dolpin.repository.CommentRepository;
import com.great.deploy.dolpin.repository.LikeItRepository;
import com.great.deploy.dolpin.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = "/api/comment/likeit", produces = "application/json")
public class LikeItController {

    @Autowired
    LikeItRepository likeItRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    AccountService accountService;

    @PostMapping
    public Response<LikeItResponse> setLikeIt(@ApiIgnore @CurrentUser Account account,
                                              @RequestBody LikeItRequest likeItRequest) {
        Account.validateAccount(account);
        Long commentId = likeItRequest.getCommentId();
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new BadRequestException("NOT FOUND commentId"));
        LikeIt likeIt = new LikeIt(comment, account);
        LikeIt save = likeItRepository.save(likeIt);
        commentRepository.save(save.getComment());
        LikeItResponse likeItResponse = new LikeItResponse(true, account.getNickname(), save.getComment().getRecommendCount());
        return new Response<>(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.getReasonPhrase(), likeItResponse);
    }

    @DeleteMapping
    public Response<LikeItResponse> cancelLikeIt(@ApiIgnore @CurrentUser Account account,
                                          @RequestBody Long commentId){

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new BadRequestException("Not found CommentId"));
        LikeIt likeIt = likeItRepository.findByCommentAndAccount(comment, account);
        likeItRepository.delete(likeIt);
        commentRepository.save(comment);
        LikeItResponse likeItResponse = new LikeItResponse(true, account.getNickname(), likeIt.getComment().getRecommendCount());
        return new Response<>(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.getReasonPhrase(), likeItResponse);
    }
}
