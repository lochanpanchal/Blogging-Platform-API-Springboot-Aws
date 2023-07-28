package com.lochan.BloggingPlatformAPI.service;

import com.lochan.BloggingPlatformAPI.model.Comment;

import com.lochan.BloggingPlatformAPI.model.Post;
import com.lochan.BloggingPlatformAPI.repository.ICommentRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    ICommentRepo iCommentRepo;


    public String addComment(Comment comment) {
        iCommentRepo.save(comment);
        return "Comment has been added";
    }


    public Comment findCommnet(Long commentId) {
        return iCommentRepo.findById(commentId).orElse(null);
    }

    public void removeComment(Comment comment) {
        iCommentRepo.delete(comment);
    }

    public void updateComment(Comment updatecomment) {
        iCommentRepo.save(updatecomment);
    }

    public List<Comment> getAllComments(Post post) {
        List<Comment> postComments = iCommentRepo.findByBlogPostPostId(post.getPostId());
        return postComments;
    }
}
