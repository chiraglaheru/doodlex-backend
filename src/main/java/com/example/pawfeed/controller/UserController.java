package com.example.pawfeed.controller;

import com.example.pawfeed.dto.ProfileResponse;
import com.example.pawfeed.entity.Post;
import com.example.pawfeed.entity.User;
import com.example.pawfeed.repository.PostRepository;
import com.example.pawfeed.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.pawfeed.entity.Follow;
import com.example.pawfeed.repository.FollowRepository;



import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }
    @GetMapping
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    @GetMapping("/{id}")
    public User getUserId(@PathVariable Long id){
        User user = userRepository.findById(id).orElse(null);

        if (user == null){
            throw new RuntimeException("User not found");
        }

        return user;
    }



    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id){
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
        return "user deleted";
    }
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User updatedUser){
        User user = userRepository.findById(id).orElse(null);

        if (user == null){
            throw new RuntimeException("User not found");
        }

        user.setName(updatedUser.getName());
        user.setBio(updatedUser.getBio());
        user.setEmail(updatedUser.getEmail());
        user.setPassword(updatedUser.getPassword());

        return userRepository.save(user);
    }
    @PostMapping("/login")
    public User login(@RequestBody User loginUser){

        User user = userRepository.findByEmail(loginUser.getEmail());

        if(user == null){
            throw new RuntimeException("User not found");
        }

        if(!user.getPassword().equals(loginUser.getPassword())){
            throw new RuntimeException("Invalid password");
        }

        return user;
    }


    //followers

    @Autowired
    private FollowRepository followRepository;

    @PostMapping("/{id}/follow")
    public String followUser(@PathVariable Long id, @RequestParam Long followerId){
        if (followRepository.existsByFollowerIdAndFollowingId(followerId,id)){
            return "Already following";
        }
        Follow follow = new Follow();
        follow.setFollowerId(followerId);
        follow.setFollowingId(id);

        followRepository.save(follow);

        return "followed sucessfully";
    }

    @DeleteMapping("/{id}/unfollow")
    public String unfollowUser(@PathVariable Long id , @RequestParam Long followerId){
        if(!followRepository.existsByFollowerIdAndFollowingId(followerId,id)){
            return "Not Following";
        }
        followRepository.deleteByFollowerIdAndFollowingId(followerId,id);
        return "unfollowed";
    }

    @GetMapping ("/{id}/followers")
    public List <Follow> getFollowers(@PathVariable Long id){
        return followRepository.findByFollowingId(id);
    }


    @Autowired
    private PostRepository postRepository;
    
    @GetMapping("/{id}/profile")
    public ProfileResponse getProfile(@PathVariable Long id){

        User user = userRepository.findById(id).orElse(null);
        if(user == null){
            throw new RuntimeException("User not found");
        }

        List<Post> posts = postRepository.findByUserIdOrderByIdDesc(id);

        int followers = followRepository.countByFollowingId(id);
        int following = followRepository.countByFollowerId(id);

        ProfileResponse response = new ProfileResponse();
        response.setUser(user);
        response.setPosts(posts);
        response.setFollowersCount(followers);
        response.setFollowingCount(following);

        return response;
    }

    @GetMapping("/{id}/is-following")
    public boolean isFollowing(
            @PathVariable Long id,
            @RequestParam Long followerId
    ){
        return followRepository.existsByFollowerIdAndFollowingId(followerId,id);
    }
}