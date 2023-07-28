package com.lochan.BloggingPlatformAPI.repository;

import com.lochan.BloggingPlatformAPI.model.AuthenticationToken;
import com.lochan.BloggingPlatformAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAuthenticationRepo extends JpaRepository<AuthenticationToken,Long> {


    AuthenticationToken findFirstByTokenValue(String authTokenValue);

    AuthenticationToken findFirstByUser(User user);
}
