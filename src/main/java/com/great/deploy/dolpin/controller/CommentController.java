package com.great.deploy.dolpin.controller;

import com.great.deploy.dolpin.domain.Comment;
import com.great.deploy.dolpin.dto.Response;
import com.great.deploy.dolpin.exception.ResourceNotFoundException;
import com.great.deploy.dolpin.repository.CommentRepository;
import com.great.deploy.dolpin.repository.PinsRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "CommentController", description = "특정 Pin's Comment API.")
@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PinsRepository pinsRepository;

    @GetMapping("/pins/{pinsId}/comments")
    public Comment getAllCommentsByPostId(@PathVariable(value = "pinsId") Long pinsId) {
        return commentRepository.findByPinsId(pinsId);
    }

    @PostMapping("/pins/{pinsId}/comments")
    public Comment createComment(@PathVariable(value = "pinsId") Long pinsId,
                                 @Valid @RequestBody Comment comment) {
        return pinsRepository.findById(pinsId)
                .map(pins -> {
                    comment.setPins(pins);
                    return commentRepository.save(comment);
                }).orElseThrow(() -> new ResourceNotFoundException("PostId " + pinsId + " not found"));
    }

    @PutMapping("/pins/{pinsId}/comments/{commentId}")
    public Comment updateComment(@PathVariable(value = "pinsId") Long pinsId,
                                 @PathVariable(value = "commentId") Long commentId,
                                 @Valid @RequestBody Comment commentRequest) {
        if (!pinsRepository.existsById(pinsId)) {
            throw new ResourceNotFoundException("PostId " + pinsId + " not found");
        }

        return commentRepository.findById(commentId)
                .map(comment -> {
                    comment.setContents(commentRequest.getContents());
                    comment.setAccountId(commentRequest.getAccountId());
                    return commentRepository.save(comment);
                }).orElseThrow(() -> new ResourceNotFoundException("CommentId " + commentId + "not found"));
    }

    @DeleteMapping("/pins/{pinsId}/comments/{commentId}")
    public Response<Boolean> deleteComment(@PathVariable(value = "pinsId") Long postId,
                                           @PathVariable(value = "commentId") Long commentId) {
        return commentRepository.findByIdAndPinsId(commentId, postId)
                .map(comment -> {
                    commentRepository.delete(comment);
                    return new Response<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), true);
                }).orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + commentId + " and pinsId " + postId));
    }
}
