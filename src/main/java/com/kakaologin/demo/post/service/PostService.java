package com.kakaologin.demo.post.service;

import com.kakaologin.demo.post.dto.PostDto;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);



    List<PostDto> getAllPosts(int page);

    boolean deletePost(Long id);
}
