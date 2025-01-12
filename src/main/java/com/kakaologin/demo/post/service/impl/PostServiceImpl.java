package com.kakaologin.demo.post.service.impl;

import com.kakaologin.demo.post.dto.PostDto;
import com.kakaologin.demo.post.entity.Post;
import com.kakaologin.demo.post.repository.PostRepository;
import com.kakaologin.demo.post.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<PostDto> getAllPosts(int page, int size) {
        // PageRequest로 페이징 요청 생성 (최신 글 순으로 정렬)
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        // 데이터베이스에서 페이징 처리된 결과 조회
        Page<Post> postPage = postRepository.findAll(pageRequest);

        // Page<Post> -> List<PostDto> 변환
        return postPage.getContent().stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
    }


}
