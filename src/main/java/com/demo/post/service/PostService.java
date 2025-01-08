package com.kakaologin.demo.post.service;

import com.kakaologin.demo.post.dto.PostDto;

public interface PostService {
    PostDto createPost(PostDto postDto, String username);
}
