package com.lochan.BloggingPlatformAPI.service;

import com.lochan.BloggingPlatformAPI.model.*;
import com.lochan.BloggingPlatformAPI.model.dto.SignInInput;
import com.lochan.BloggingPlatformAPI.model.dto.SignUpOutput;
import com.lochan.BloggingPlatformAPI.repository.IUserRepo;
import com.lochan.BloggingPlatformAPI.service.emailUtility.EmailHandler;
import com.lochan.BloggingPlatformAPI.service.hashingUtility.PasswordEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    IUserRepo iUserRepo;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    PostService postService;

    @Autowired
    CommentService commentService;

    @Autowired
    FollowService followService;

    public SignUpOutput signUpUser(User user) {

        boolean signUpStatus = true;
        String signUpStatusMessage = null;

        String newEmail = user.getUserEmail();

        //check if input email is empty!!
        if(newEmail == null){
            signUpStatusMessage = "Invalid email";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }

        //check if this user email already exists ??
        User existingUser = iUserRepo.findFirstByUserEmail(newEmail);

        if(existingUser != null)
        {
            signUpStatusMessage = "Email already registered!!!";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }

        //hash the password: encrypt the password
        try {
            String encryptedPassword = PasswordEncrypter.encryptPassword(user.getUserPassword());


            //save encrypted password
            user.setUserPassword(encryptedPassword);
            iUserRepo.save(user);

            return new SignUpOutput(signUpStatus, "User registered successfully!!!");
        }
        catch (Exception e)
        {
            signUpStatusMessage = "Internal error occured during signup";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }

    }

    public String signInUser(SignInInput signInInput) {
        String signInStausMessage = null;

        String signInEmail = signInInput.getEmail();


        //no email given as input
        if(signInEmail == null)
        {
            signInStausMessage = "Invalid email";
            return signInStausMessage;
        }

        //check if this user email already exists ??
        User existingUser = iUserRepo.findFirstByUserEmail(signInEmail);

        if(existingUser == null)
        {
            signInStausMessage = "Email not registered!!!";
            return signInStausMessage;
        }

        // match passwords
        //hash the password : encrpt the password

        try{
            String encryptedPassword = PasswordEncrypter.encryptPassword(signInInput.getPassword());

            if(existingUser.getUserPassword().equals(encryptedPassword))
            {
                //session should be created since password matched and user id is valid
                AuthenticationToken authToken = new AuthenticationToken(existingUser);
                authenticationService.saveAuthToken(authToken);
                String toEmail = signInInput.getEmail();
                EmailHandler.sendEmail(toEmail,"email testing",authToken.getTokenValue());
                return "Token send to your email";
            }
            else{
                signInStausMessage = "Invalid credentials!!";
                return signInStausMessage;
            }

        }
        catch (Exception e){

            signInStausMessage = "Internal error occured during sign in";
            return signInStausMessage;

        }
    }

    public User getUserByUsername(String username) {
        return iUserRepo.findByUsername(username);
    }


    public String createBlogPost(Post post, String email) {
        User postOwner = iUserRepo.findFirstByUserEmail(email);
        post.setPostOwner(postOwner);
        return postService.createBlogPost(post);
    }

    public String removeBlogPost(Long postId, String email) {
        Post post = postService.getPostById(postId);
        if(post != null) {
            String userEmail = post.getPostOwner().getUserEmail();
            if(userEmail.equals(email)) {
                return postService.removeBlogPost(post);
            }
            else{
                return "this is not your post";
            }

        }
        else
        {
            return "post not exist";
        }
    }

    public String addComment(Comment comment, String commenterEmail) {
        boolean postValid = postService.validatePost(comment.getBlogPost());
        if(postValid) {
            User commenter = iUserRepo.findFirstByUserEmail(commenterEmail);
            comment.setCommenter(commenter);
            return commentService.addComment(comment);
        }
        else {
            return "Cannot comment on Invalid Post!!";
        }
    }

    public String removeBlogComment(Long commentId, String email) {
        Comment comment = commentService.findCommnet(commentId);
        if(comment != null)
        {
            if(authorizeCommentRemover(email,comment))
            {
                commentService.removeComment(comment);
                return "comment deleted successfully";
            }
            else
            {
                return "Unauthorized delete detected...Not allowed!!!!";
            }
        }
        else
        {
            return "Invalid Comment";
        }
    }


    private boolean authorizeCommentRemover(String email, Comment comment) {
        String commentOwnerEmail = comment.getCommenter().getUserEmail();
        String postOwnerEmail = comment.getBlogPost().getPostOwner().getUserEmail();

        return postOwnerEmail.equals(email) || commentOwnerEmail.equals(email);
    }

    public String followUser(String follow, String followerEmail) {
        User followTargetUser = iUserRepo.findFirstByUserHandle(follow);

        User follower = iUserRepo.findFirstByUserEmail(followerEmail);//i want to follow

        if(followTargetUser!=null)
        {
            followTargetUser.setFollowerCount((follower.getFollowerCount()==null)?1:follower.getFollowerCount()+1);
            iUserRepo.save(follower);
            return "you are now following" + followTargetUser.getUserHandle();
        }
        else {
            return "User to be followed is Invalid!!!";
        }

    }

    public String updateBlogPost(Long postId, String email,String postUpdateContent) {

        Post post = postService.getPostById(postId);
        if(post == null)
        {
            return "Post not exist";
        }
        User user = iUserRepo.findFirstByUserEmail(email);
        boolean isPostOwner = postService.isPostOwner(post,user);
        if(isPostOwner) {
            return postService.updateBlogPost(post,postUpdateContent);
        }

        return "post is not of postOwner";
    }


    public String updateBlogComment(Comment updatecomment,Long commentId, String email) {
        Comment comment = commentService.findCommnet(commentId);
        if(comment != null)
        {
            if(authorizeCommentUpdate(email,comment))
            {
                commentService.updateComment(updatecomment);
                return "comment updated successfully";
            }
            else
            {
                return "Unauthorized update detected...Not allowed!!!!";
            }
        }
        else
        {
            return "Invalid Comment";
        }
    }

    private boolean authorizeCommentUpdate(String email, Comment comment) {
        String commentOwnerEmail = comment.getCommenter().getUserEmail();

        return commentOwnerEmail.equals(email);
    }

    public List<Post> getAllPostsMy(String email) {
        User user = iUserRepo.findFirstByUserEmail(email);
        return postService.getAllPostsMy(user);
    }

    public String signOutUser(String email) {
        User user = iUserRepo.findFirstByUserEmail(email);
        AuthenticationToken token = authenticationService.findFirstByUser(user);
        authenticationService.removeToken(token);
        return "sign-out successful";
    }

    public List<Comment> getAllCommentsOnPost(Long postId) {
        Post post = postService.getPostById(postId);
        if(post != null){
            return commentService.getAllComments(post);
        }
        throw new IllegalStateException("invalid post");
    }
}
