package com.great.deploy.dolpin.repository;

import com.great.deploy.dolpin.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Comment findByPinsId(Long pinsId);
    Optional<Comment> findByIdAndPinsId(Long id, Long postId);
}
