package com.kakaologin.demo.post.controller;

import com.kakaologin.demo.post.dto.PostDto;
import com.kakaologin.demo.post.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 게시글 작성 API
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @RequestHeader("username") String username) {
        PostDto createdPost = postService.createPost(postDto, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

}

