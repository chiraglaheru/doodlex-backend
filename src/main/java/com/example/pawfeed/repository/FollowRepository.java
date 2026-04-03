package com.example.pawfeed.repository;
import com.example.pawfeed.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;


public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findByFollowerId(Long followerId);
    List<Follow> findByFollowingId(Long followingId);
    boolean existsByFollowerIdAndFollowingId(Long followerId, Long followingId);

    @Modifying
    @Transactional
    void deleteByFollowerIdAndFollowingId(Long followerId, Long followingId);
    int countByFollowingId(Long followingId);
    int countByFollowerId(Long followerId);
}