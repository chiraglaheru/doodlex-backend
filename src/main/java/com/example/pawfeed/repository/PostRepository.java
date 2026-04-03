package com.example.pawfeed.repository;

import com.example.pawfeed.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByOrderByIdDesc();
    List<Post> findByUserIdInOrderByIdDesc(List<Long> userIds);
    List<Post> findByUserIdOrderByIdDesc(Long userId);
}