package com.lochan.BloggingPlatformAPI.service;

import com.lochan.BloggingPlatformAPI.model.Follow;
import com.lochan.BloggingPlatformAPI.model.User;
import com.lochan.BloggingPlatformAPI.repository.IFollowRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowService {
    @Autowired
    IFollowRepo iFollowRepo;

    public void startFollowing(Follow follow, User follower) {
        follow.setCurrentUserFollower(follower);
        iFollowRepo.save(follow);
    }

    public boolean isFollowAllowed(User targetUser, User follower) {
        List<Follow> followList =  iFollowRepo.findByCurrentUserAndCurrentUserFollower(targetUser,follower);

        return followList!=null && followList.isEmpty() && !targetUser.equals(follower);
    }


}
