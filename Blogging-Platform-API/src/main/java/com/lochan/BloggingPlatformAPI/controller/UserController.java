package com.lochan.BloggingPlatformAPI.controller;

import com.lochan.BloggingPlatformAPI.model.Comment;
import com.lochan.BloggingPlatformAPI.model.Follow;
import com.lochan.BloggingPlatformAPI.model.Post;
import com.lochan.BloggingPlatformAPI.model.User;
import com.lochan.BloggingPlatformAPI.model.dto.SignInInput;
import com.lochan.BloggingPlatformAPI.model.dto.SignUpOutput;
import com.lochan.BloggingPlatformAPI.service.AuthenticationService;
import com.lochan.BloggingPlatformAPI.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/signup")
    public SignUpOutput signUpBlogUser(@RequestBody User user)
    {

        return userService.signUpUser(user);
    }

    @PostMapping("/signIn")
    public String signInBlogUser(@RequestBody @Valid SignInInput signInInput)
    {
        return userService.signInUser(signInInput);
    }

    //sigout
    @DeleteMapping("/sign-out")
    public String signOutUser(@RequestParam String email,
                              @RequestParam String token)
    {
        if(authenticationService.authenticate(email,token))
        {
            return userService.signOutUser(email);
        }
        else
        {
            return "not valid used to sign-out or email and token is not same";
        }
    }

    // Endpoint to get user information by username
    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }


    // user can follow other users
    @PostMapping("follow")
    public String followUser(@RequestParam String follow,
                             @RequestParam String followerEmail,
                             @RequestParam String followerToken)
    {
        if(authenticationService.authenticate(followerEmail,followerToken)) {
            return userService.followUser(follow,followerEmail);
        }
        else {
            return "Not an Authenticated user activity!!!";
        }
    }



    //post create
    @PostMapping("/post")
    public String createBlogPost(@RequestBody Post post, @RequestParam String email, @RequestParam String token)
    {
        if(authenticationService.authenticate(email,token)) {
            return userService.createBlogPost(post,email);
        }
        else {
            return "Not an Authenticated user activity!!!";
        }

    }

    //delete post
    @DeleteMapping("/post")
    public String removeBlogPost(@RequestParam Long postId, @RequestParam String email, @RequestParam String token)
    {
        if(authenticationService.authenticate(email,token)) {
            return userService.removeBlogPost(postId,email);
        }
        else {
            return "Not an Authenticated user activity!!!";
        }
    }

    //update post
    @PutMapping("/{postId}")
    public String updateBlogPost(@RequestParam Long postId,
                                 @RequestParam String email,
                                 @RequestParam String token,
                                 @RequestParam String postUpdateContent){
        if(authenticationService.authenticate(email,token)) {
            return userService.updateBlogPost(postId,email,postUpdateContent);
        }
        else {
            return "Not an Authenticated user activity!!!";
        }
    }

    //get all post of user login
    @GetMapping("/getPostsMy")
    public List<Post> getAllPostsMy(@RequestParam String email, @RequestParam String token){
        if(authenticationService.authenticate(email,token)) {
            return userService.getAllPostsMy(email);
        }
        throw new IllegalStateException("Not an Authenticated user activity!!!");

    }


    //added comment to post
    @PostMapping("/comment")
    public String addComment(@RequestBody Comment comment, @RequestParam String commenterEmail, @RequestParam String commenterToken)
    {
        if(authenticationService.authenticate(commenterEmail,commenterToken)) {
            return userService.addComment(comment,commenterEmail);
        }
        else {
            return "Not an Authenticated user activity!!!";
        }
    }

    //delete comment on post
    //comment
    @DeleteMapping("/comment")
    public String removeBlogComment(@RequestParam Long commentId, @RequestParam String email, @RequestParam String token)
    {
        if(authenticationService.authenticate(email,token)) {
            return userService.removeBlogComment(commentId,email);
        }
        else {
            return "Not an Authenticated user activity!!!";
        }
    }

    //update
   @PutMapping("/comment")
   public String updateBlogComment(@RequestBody Comment comment,@RequestParam Long commentId, @RequestParam String email, @RequestParam String token)
   {
       if(authenticationService.authenticate(email,token)) {
           return userService.updateBlogComment(comment,commentId,email);
       }
       else {
           return "Not an Authenticated user activity!!!";
       }
   }

   @GetMapping("/comment/{postId}")
    public List<Comment> getAllCommentsOnPost(@RequestParam String email,
                                              @RequestParam String token,
                                              @PathVariable Long postId)
   {
       if(authenticationService.authenticate(email,token)) {
           return userService.getAllCommentsOnPost(postId);
       }
       throw new IllegalStateException("Not an Authenticated user activity!!");
   }






}
