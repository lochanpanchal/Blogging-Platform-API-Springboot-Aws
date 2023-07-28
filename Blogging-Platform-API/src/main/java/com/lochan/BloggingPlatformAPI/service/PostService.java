package com.lochan.BloggingPlatformAPI.service;


import com.lochan.BloggingPlatformAPI.model.Post;
import com.lochan.BloggingPlatformAPI.model.User;
import com.lochan.BloggingPlatformAPI.repository.IPostRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    IPostRepo iPostRepo;

    public String createBlogPost(Post post) {
        iPostRepo.save(post);
        return "Post uploaded";
    }

    public String removeBlogPost(Post post) {
        iPostRepo.delete(post);
        return "deleted post success";
    }

    public boolean validatePost(Post BlogPost) {
        return (BlogPost != null && iPostRepo.existsById(BlogPost.getPostId()));
    }

    public Post getPostById(Long postId) {
        return iPostRepo.findById(postId).orElse(null);
    }

    public String updateBlogPost(Post post,String postUpdateContent) {
        post.setTitle(postUpdateContent);
        iPostRepo.save(post);

        return "post updated";
    }


    public boolean isPostOwner(Post post, User user) {
        return post.getPostOwner().equals(user);
    }

    public List<Post> getAllPostsMy(User user) {
        List<Post> posts = iPostRepo.findByPostOwner(user);
        return posts;
    }
}
