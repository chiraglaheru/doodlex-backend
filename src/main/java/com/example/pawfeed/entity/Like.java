package com.example.pawfeed.entity;

import jakarta.persistence.*;
import lombok.Setter;

@Entity
@Table(name = "likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private Long userId;
    @Setter
    private Long postId;

    // getters & setters
    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public Long getPostId() { return postId; }
}