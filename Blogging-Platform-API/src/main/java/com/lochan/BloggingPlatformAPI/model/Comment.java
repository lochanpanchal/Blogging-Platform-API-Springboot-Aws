package com.lochan.BloggingPlatformAPI.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(nullable = false)
    private String content;

    @ManyToOne()
    @JoinColumn(name = "fk_comment_id", nullable = false)
    private User commenter;

    @ManyToOne
    @JoinColumn(name = "fk_comment_post_id", nullable = false)
    private Post blogPost;

}
