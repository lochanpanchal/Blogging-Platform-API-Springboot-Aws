package com.lochan.BloggingPlatformAPI.repository;

import com.lochan.BloggingPlatformAPI.model.Follow;
import com.lochan.BloggingPlatformAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepo extends JpaRepository<User,Long> {




    User findByUsername(String username);

    User findFirstByUserEmail(String newEmail);

    User findFirstByUserHandle(String follow);
}
