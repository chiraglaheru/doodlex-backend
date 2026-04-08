package com.example.pawfeed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private Long userId;
    private Long postId;
    private String text;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                



}
