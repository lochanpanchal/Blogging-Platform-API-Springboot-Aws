package com.lochan.BloggingPlatformAPI.repository;

import com.lochan.BloggingPlatformAPI.model.Follow;
import com.lochan.BloggingPlatformAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFollowRepo extends JpaRepository<Follow,Long> {

    List<Follow> findByCurrentUserAndCurrentUserFollower(User targetUser, User follower);
}
