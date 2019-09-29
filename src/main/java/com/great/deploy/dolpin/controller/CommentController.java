package com.great.deploy.dolpin.controller;

import com.great.deploy.dolpin.account.Account;
import com.great.deploy.dolpin.account.CurrentUser;
import com.great.deploy.dolpin.domain.Comment;
import com.great.deploy.dolpin.dto.CommentListResponse;
import com.great.deploy.dolpin.dto.CommentRequest;
import com.great.deploy.dolpin.dto.CommentResponse;
import com.great.deploy.dolpin.dto.model.Response;
import com.great.deploy.dolpin.exception.BadRequestException;
import com.great.deploy.dolpin.exception.ResourceNotFoundException;
import com.great.deploy.dolpin.repository.CommentRepository;
import com.great.deploy.dolpin.repository.LikeItRepository;
import com.great.deploy.dolpin.repository.PinsRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Api(value = "CommentController", description = "특정 Pin's Comment API.")
@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PinsRepository pinsRepository;

    @Autowired
    private LikeItRepository likeItRepository;

    @ApiOperation(value = "특정 Pin 복수의 댓글 가져오기", response = CommentListResponse.class)
    @GetMapping("/pins/{pinsId}/comments")
    public Response<CommentListResponse> getAllCommentsByPostId(
            @ApiIgnore @CurrentUser Account account,
            @PathVariable(value = "pinsId") Long pinsId) {

        Account.validateAccount(account);
        pinsRepository.findById(pinsId).orElseThrow(() -> new ResourceNotFoundException("해당 핀을 찾을 수 없습니다."));
        List<Comment> comments = commentRepository.findAllByPinsId(pinsId);
        comments.sort(Comparator.comparing(Comment::getCreateAt).reversed());

        return new Response<>(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                new CommentListResponse(
                        comments.stream().map(
                                comment -> new CommentResponse(comment, likeItRepository.findByCommentAndAccount(comment, account) != null)
                        ).collect(Collectors.toList()))
        );
    }

    @ApiOperation(value = "특정 pin 댓글 등록", response = CommentResponse.class)
    @PostMapping("/pins/{pinsId}/comments")
    public Response<CommentListResponse> createComment(
            @ApiIgnore @CurrentUser Account account,
            @PathVariable(value = "pinsId") Long pinsId,
            @Valid @RequestBody CommentRequest request) {

        Account.validateAccount(account);
        pinsRepository.findById(pinsId).orElseThrow(() -> new ResourceNotFoundException("해당 핀을 찾을 수 없습니다."));
        return new Response<>(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                pinsRepository.findById(pinsId)
                        .map(pins -> {
                            Comment save = new Comment(pins, request.getContents(), request.getAccountId(), request.getNickname());
                            commentRepository.save(save);
                            List<Comment> comments = commentRepository.findAllByPinsId(pins.getId());
                            comments.sort(Comparator.comparing(Comment::getCreateAt).reversed());

                            return new CommentListResponse(
                                    comments.stream().map(
                                            comment -> {
                                                boolean isRecommended = likeItRepository.findByCommentAndAccount(comment, account) != null;
                                                return new CommentResponse(comment, isRecommended);
                                            }
                                    ).collect(Collectors.toList())
                            );
                        }).orElseThrow(() -> new ResourceNotFoundException("해당 핀을 찾을 수 없습니다.")));
    }

    @ApiOperation(value = "특정 pin 댓글 수정", response = CommentResponse.class)
    @PutMapping("/pins/{pinsId}/comments/{commentId}")
    public Response<CommentResponse> updateComment(
            @ApiIgnore @CurrentUser Account account,
            @PathVariable(value = "pinsId") Long pinsId,
            @PathVariable(value = "commentId") Long commentId,
            @Valid @RequestBody CommentRequest commentRequest,
            Errors errors) {

        if (errors.hasErrors()) {
            throw new BadRequestException("null 체크 필요");
        }

        Account.validateAccount(account);

        pinsRepository.findById(pinsId).orElseThrow(() -> new ResourceNotFoundException("해당 핀을 찾을 수 없습니다."));

        return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), commentRepository.findById(commentId)
                .map(comment -> {
                    comment.setContents(commentRequest.getContents());
                    comment.setAccountId(commentRequest.getAccountId());
                    Comment newComment = commentRepository.save(comment);
                    boolean isRecommended = likeItRepository.findByCommentAndAccount(newComment, account) != null;
                    return new CommentResponse(newComment, isRecommended);
                }).orElseThrow(() -> new ResourceNotFoundException("해당 댓글을 찾을 수 없습니다.")));
    }

    @ApiOperation(value = "특정 pin 댓글 삭제")
    @DeleteMapping("/pins/{pinsId}/comments/{commentId}")
    public Response<Boolean> deleteComment(
            @ApiIgnore @CurrentUser Account account,
            @PathVariable(value = "pinsId") Long pinId,
            @PathVariable(value = "commentId") Long commentId) {
        Account.validateAccount(account);
        pinsRepository.findById(pinId).orElseThrow(() -> new ResourceNotFoundException("해당 핀을 찾을 수 없습니다."));
        return commentRepository.findByIdAndPinsId(commentId, pinId)
                .map(comment -> {
                    commentRepository.delete(comment);
                    return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), true);
                }).orElseThrow(() -> new ResourceNotFoundException("댓글을 찾을 수 없습니다."));
    }
}
