package com.example.pawfeed.controller;
import com.example.pawfeed.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.pawfeed.entity.Comment;

import java.util.List;
import java.util.zip.ZipEntry;


@RestController
@RequestMapping("/comments")

public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @PostMapping
    public Comment addComment(@RequestBody Comment comment){
            return commentRepository.save(comment);
    }

//    @GetMapping("/test")
//    public String test() {
//        return "working";
//    }
    @GetMapping("/{postId}")
    public List<Comment> getComments(@PathVariable Long postId) {
        return commentRepository.findByPostId(postId);
    }
}
