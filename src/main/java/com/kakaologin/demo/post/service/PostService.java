package com.kakaologin.demo.post.service;

import com.kakaologin.demo.post.dto.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto, String username);

    List<PostDto> getAllPosts(int page, int size);
}
