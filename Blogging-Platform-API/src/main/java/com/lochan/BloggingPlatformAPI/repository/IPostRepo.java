package com.lochan.BloggingPlatformAPI.repository;

import com.lochan.BloggingPlatformAPI.model.Post;
import com.lochan.BloggingPlatformAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPostRepo extends JpaRepository<Post,Long> {


    List<Post> findByPostOwner(User user);
}
