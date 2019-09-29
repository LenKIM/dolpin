package com.great.deploy.dolpin.repository;

import com.great.deploy.dolpin.domain.Comment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class CommentRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    public CommentRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        super(Comment.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

//    public List<Comment> countLikeByAccountId(Long accountId){
//        return jpaQueryFactory.selectFrom(QComment.comment)
//                .where(QComment.comment.nickname.eq(name))
//                .fetch();
//    }
}
