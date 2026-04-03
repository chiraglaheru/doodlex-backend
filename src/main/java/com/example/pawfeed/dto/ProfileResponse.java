package com.example.pawfeed.dto;

import com.example.pawfeed.entity.User;
import com.example.pawfeed.entity.Post;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class ProfileResponse {

    private User user;
    private List<Post> posts;
    private int followersCount;
    private int followingCount;
}
