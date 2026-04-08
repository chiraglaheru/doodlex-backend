package com.example.pawfeed.controller;

import com.example.pawfeed.entity.Follow;
import com.example.pawfeed.entity.Post;
import com.example.pawfeed.entity.Like;
import com.example.pawfeed.repository.FollowRepository;
import com.example.pawfeed.repository.LikeRepository;
import com.example.pawfeed.repository.PostRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = "http://localhost:3000")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private FollowRepository followRepository;

    @PostMapping(consumes = "multipart/form-data")
    public Post uploadPost(
            @RequestParam("file") MultipartFile file,
            @RequestParam("caption") String caption,
            @RequestParam("userId") Long userId
    ) throws Exception {

        String uploadDir = System.getProperty("user.dir") + "/uploads/";
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        String filePath = uploadDir + fileName;

        file.transferTo(new File(filePath));

        Post post = new Post();
        post.setUserId(userId);
        post.setCaption(caption);
        post.setImageUrl("http://localhost:8081/uploads/" + fileName);

        return postRepository.save(post);
    }

    @GetMapping
    public List<Post> getAllPosts(){
        return postRepository.findAllByOrderByIdDesc();
    }

    @PostMapping("/{postId}/like")
    public String likePost(@PathVariable Long postId, @RequestParam Long userId){

        if (likeRepository.existsByUserIdAndPostId(userId, postId)) {
            return "Already Liked";
        }

        Like like = new Like();
        like.setUserId(userId);
        like.setPostId(postId);

        likeRepository.save(like);

        return "Post liked";
    }

    @GetMapping("/{postId}/likes")
    public int getLikes(@PathVariable Long postId){
        return likeRepository.findByPostId(postId).size();
    }

    @GetMapping("/feed")
    public List<Post> getFeed(@RequestParam Long userId){
        List<Follow> follows = followRepository.findByFollowerId(userId);

        List<Long> followingIds = follows.stream()
                .map(Follow::getFollowingId)
                .toList();

        followingIds = new java.util.ArrayList<>(followingIds);
        followingIds.add(userId);

        return postRepository.findByUserIdInOrderByIdDesc(followingIds);
    }
    @DeleteMapping("/{postId}/like")
    public String unlikePost(@PathVariable Long postId, @RequestParam Long userId) {
        if (!likeRepository.existsByUserIdAndPostId(userId, postId)) {
            return "Not Liked";
        }
        likeRepository.deleteByUserIdAndPostId(userId, postId);
        return "Post unliked";
    }
}