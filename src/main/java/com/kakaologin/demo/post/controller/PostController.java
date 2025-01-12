package com.kakaologin.demo.post.controller;

import com.kakaologin.demo.post.dto.PostDto;
import com.kakaologin.demo.post.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService)
    {
        this.postService = postService;
    }

    // 게시글 작성 API
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
        PostDto createdPost = postService.createPost(postDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    //게시글 목록
    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(
            @RequestParam("page") int page)
    {
        List<PostDto> posts = postService.getAllPosts(page);
        return ResponseEntity.ok(posts);
    }


    //게시글 삭제
    @PostMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") Long id) {
        boolean isDeleted = postService.deletePost(id);
        if (isDeleted) {
            return ResponseEntity.ok("게시글이 삭제되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("게시글을 찾을 수 없습니다.");
        }
    }

}

