package com.kakaologin.demo.post.service.impl;

import com.kakaologin.demo.post.dto.PostDto;
import com.kakaologin.demo.post.entity.Post;
import com.kakaologin.demo.post.repository.PostRepository;
import com.kakaologin.demo.post.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto, String username) {
        // DTO → Entity 변환
        Post post = modelMapper.map(postDto, Post.class);
        post.setAuthor(username); // 작성자 정보 설정
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        // DB 저장
        Post savedPost = postRepository.save(post);

        // Entity → DTO 변환
        return modelMapper.map(savedPost, PostDto.class);
    }


}
