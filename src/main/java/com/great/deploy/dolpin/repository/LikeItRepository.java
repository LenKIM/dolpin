package com.great.deploy.dolpin.repository;

import com.great.deploy.dolpin.account.Account;
import com.great.deploy.dolpin.domain.Comment;
import com.great.deploy.dolpin.domain.LikeIt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeItRepository extends JpaRepository<LikeIt, Long> {

    LikeIt findByCommentAndAccount(Comment comment, Account account);

}
