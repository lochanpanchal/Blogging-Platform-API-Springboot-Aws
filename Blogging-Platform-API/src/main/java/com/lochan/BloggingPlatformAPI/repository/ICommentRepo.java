package com.lochan.BloggingPlatformAPI.repository;


import com.lochan.BloggingPlatformAPI.model.Comment;
import com.lochan.BloggingPlatformAPI.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICommentRepo extends JpaRepository<Comment,Long> {


    List<Comment> findByBlogPostPostId(Long postId);
}
